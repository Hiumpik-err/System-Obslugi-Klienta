using Shopper.Models;
using Shopper.UsageClasses;
using System.Collections.ObjectModel;

namespace Shopper.Views;

public partial class ShoppingCartPage : ContentPage
{

    public ObservableCollection<CartItem> CartItems { get; set; }
    private readonly IDataCache _cache;
    public ShoppingCartPage(IDataCache cache)
	{
		InitializeComponent();
        CartItems = new ObservableCollection<CartItem>();
        _cache = cache;
    }

    protected override void OnAppearing()
    {
        base.OnAppearing();
        CartItems.Clear();
        foreach((int id, int cnt) in _cache.GetAllData())
        {
            CartItems.Add(new CartItem { Product = _cache.GetProducts().First(p => p.Id == id), Quantity = cnt });
        }

        CartCollectionView.ItemsSource = CartItems;
        UpdateTotalSummary();
    }

    private void OnDecreaseQuantityClicked(object sender, EventArgs e)
    {
        var button = (Button)sender;
        var item = (CartItem)button.CommandParameter;

        if (item.Quantity > 1) item.Quantity--;
        else CartItems.Remove(item);

        _cache.SubtractElement(item.Product.Id);

        UpdateTotalSummary();
    }

    private void OnIncreaseQuantityClicked(object sender, EventArgs e)
    {
        var button = (Button)sender;
        var item = (CartItem)button.CommandParameter;
        item.Quantity++;
        _cache.AddData(item.Product.Id);


        UpdateTotalSummary();
    }

    private void UpdateTotalSummary()
    {
        decimal total = 0;
        foreach (CartItem item in CartItems)
        {
            total += decimal.Parse(item.Product.Price.ToString()) * item.Quantity;
        }
        CartTotalLabel.Text = $"{total:F2} zł";
    }

    private async void OnCheckoutClicked(object sender, EventArgs e)
    {
        if (CartItems.Count == 0)
        {
            await DisplayAlertAsync("ERROR", "Nie masz żadnych produktów w koszyku.", "OK");
            return;
        }


        await Shell.Current.GoToAsync("checkout");
    }
}
