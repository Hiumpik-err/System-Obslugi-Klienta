using Shopper.Models;
using Shopper.Services;
using Shopper.UsageClasses;
using System.Text.Json;

namespace Shopper.Views;

public partial class CheckoutPage : ContentPage
{
    private double DeliveryCost = 14.99;
    private IDataCache _cache;
    private Account acc;
    public CheckoutPage(IDataCache cache)
    {
        InitializeComponent();
        _cache = cache;
    }

    protected override async void OnAppearing()
    {
        base.OnAppearing();


        if (await SecureStorage.GetAsync("currentUser") is null)
        {

            await DisplayAlertAsync("Wymagane logowanie", "Aby sfinalizować zamówienie, musisz być zalogowany.", "Zaloguj się");

            await Shell.Current.GoToAsync("login");
            return;
        }
        acc = await GetCurrentUser();

        FullNameEntry.Text = acc.fullName;

        if (acc.RawDeliveryDetails is not null)
        {
            StreetEntry.Text = $"{acc.deliveryDetails.street}";
            PostalCodeEntry.Text = acc.deliveryDetails.townCode;
            CityEntry.Text = acc.deliveryDetails.city;
        }

        CalculateSummary();
    }

    private void CalculateSummary()
    {
        Dictionary<int, int> cartItems = _cache.GetAllData();
        double BasketSubtotal = 0;
        foreach ((int id, int q) in cartItems)
        {
            var product = _cache.GetProducts().FirstOrDefault(p => p.Id == id);
            if (product != null)
            {
                BasketSubtotal += Convert.ToDouble(product.Price * q);
            }
        }
        double total = DeliveryCost + BasketSubtotal;

        ProductsSubtotalLabel.Text = $"{BasketSubtotal:F2} zł";
        DeliveryCostLabel.Text = $"{DeliveryCost:F2} zł";
        TotalOrderLabel.Text = $"{total:F2} zł";
    }

    private async void OnOrderSubmittedClicked(object sender, EventArgs e)
    {
        
        if (string.IsNullOrWhiteSpace(FullNameEntry.Text) ||
            string.IsNullOrWhiteSpace(StreetEntry.Text) ||
            string.IsNullOrWhiteSpace(PostalCodeEntry.Text) ||
            string.IsNullOrWhiteSpace(CityEntry.Text))
        {
            await DisplayAlertAsync("Błąd", "Proszę uzupełnić wszystkie dane dostawy.", "OK");
            return;
        }

        
        string selectedPayment = "Nie wybrano";
        if (BlikRadio.IsChecked) selectedPayment = "BLIK";
        else if (CardRadio.IsChecked) selectedPayment = "Karta płatnicza";
        else if (TransferRadio.IsChecked) selectedPayment = "Przelew online";
        else if (CodRadio.IsChecked) selectedPayment = "Za pobraniem";

       

        string homeNumber = StreetEntry.Text.Split(' ').LastOrDefault() ?? "";

        //Zamowienie
        Orders order = new Orders(
                products: string.Join(",", _cache.GetAllData().Select(kvp => $"{kvp.Key}x{kvp.Value}")),
                wholeCost: Convert.ToDecimal(ProductsSubtotalLabel.Text.Replace(" zł", "")) + Convert.ToDecimal(DeliveryCostLabel.Text.Replace(" zł", "")),
                orderDate: DateTime.Now.ToString("yyyy-MM-dd")
            );

        int orderId = await ProductsRestService.AddNewOrder(order);
        order.orderId = orderId;
        if (orderId == 0)
        {
            await DisplayAlertAsync("Błąd", "Nie można złożyć zamówienia. Prosimy spróbować ponownie później.", "OK");
            return;
        }

        //Dane Platnosci

        PaymentDetails pd = new PaymentDetails(
                paymentType: selectedPayment,
                paymentDate: DateTime.Now.ToString("yyyy-MM-dd"),
                order: order
            );

        var (paymentResponse, paymentSuccess) = await ProductsRestService.AddNewPaymentDetails(pd);

        if (acc.RawDeliveryDetails is null)
        {
            //Dane dostawy
            DeliveryDetails dd = new DeliveryDetails(
                    region: GetRegion(int.Parse(PostalCodeEntry.Text.Substring(0, 2))),
                    city: CityEntry.Text,
                    street: StreetEntry.Text,
                    townCode: string.Join("-", PostalCodeEntry.Text.Substring(0, 2), PostalCodeEntry.Text.Substring(2, 3)),
                    homeNumber: homeNumber,
                    userId: acc,
                    order: order
                );
            var (responseMessage, success) = await ProductsRestService.AddNewDeliveryDetails(dd);
            if (!success) return;
        }

        Warning warning = new Warning(
            issueTopic: "Zamówienie nr " + orderId,
            issueStatus: "Nowe",
            recivedDate: DateTime.Now.ToString("yyyy-MM-dd"),
            affectedProducts: order.products,
            description: string.IsNullOrWhiteSpace(OrderNotesEditor.Text) ? "" : OrderNotesEditor.Text,
            expectations: string.IsNullOrWhiteSpace(OrderExpectations.Text) ? "" : OrderExpectations.Text,
            userId: acc,
            orderId: order
        );

        var (warningResponse, warningSuccess) = await ProductsRestService.SendNewWarning(warning);



        if (paymentSuccess && warningSuccess)
        {
            await DisplayAlertAsync("SUCCESS", $"Zamówienie zostało złożone.\nWybrana płatność: {selectedPayment}! \nProsimy o cierpliwość w oczekiwaniu na paczkę.", "OK");
            await Shell.Current.GoToAsync("/list");
        }
        else
        {
            await DisplayAlertAsync("Błąd", $"Nie można przetworzyć płatności. Prosimy spróbować ponownie później.\nSzczegóły: {paymentResponse}", "OK");
        }

    }

    public string GetRegion(int townCodePrefix)
    {
        return townCodePrefix switch
        {
            >= 0 and <= 9 => " mazowieckie",
            >= 10 and <= 14 => " warminsko-mazurskie",
            19 => " warminsko-mazurskie",
            >= 15 and <= 18 => " podlaskie",
            >= 20 and <= 24 => " lubelskie",
            >= 25 and <= 29 => " swiętokrzyskie",
            >= 30 and <= 34 => " malopolskie",
            >= 35 and <= 39 => " podkarpackie",
            >= 45 and <= 48 => " opolskie",
            >= 40 and <= 44 => " slaskie",
            49 => " slaskie",
            >= 50 and <= 59 => " dolnoslaskie",
            >= 60 and <= 64 => " wielkopolskie",
            >= 65 and <= 69 => " lubuskie",
            >= 70 and <= 78 => " zachodniopomorskie",
            >= 80 and <= 84 => " pomorskie",
            >= 85 and <= 87 => " kujawsko-pomorskie",
            >= 90 and <= 99 => " łodzkie",
            _ => "Nieznany przedział kodu"
        };
    }

    private async Task<Account> GetCurrentUser()
    {
        var userDataJson = await SecureStorage.GetAsync("currentUser");
        var acc = JsonSerializer.Deserialize<Account>(userDataJson);
        return acc;

    }
}
