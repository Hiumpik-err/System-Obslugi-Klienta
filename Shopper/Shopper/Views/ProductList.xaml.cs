using Shopper.Models;
using Shopper.Services;
using Shopper.UsageClasses;
using System.Collections.ObjectModel;

namespace Shopper.Views;

public partial class ProductList : ContentPage
{
    private readonly RestService _service = new RestService();

    private ObservableCollection<Product> _displayedProducts = [];
    private List<Product> _allProducts = [];
    private IDataCache _cache;
    public ProductList(IDataCache cache)
	{
		InitializeComponent();
        _cache = cache;
        PobierzDane();
	}

    private async void onDisappearing(object sender, EventArgs e)
    {
        _displayedProducts.Clear();
        _allProducts.Clear();
        await DBRestService.Logout(); // Teoretycznie powinien zostac, ale boje sie ze z sesja beda problemy
    }

    private async void PobierzDane()
    {
        try
        {
            var products = await _service.GetProductsAsync();

            if (products is not null)
            {
                _allProducts.Clear();
                _allProducts.AddRange(products);

                // Aktualizacja wyświetlanej listy
                FilterAndDisplayProducts();
            }

            ProductsCollectionView.ItemsSource = _displayedProducts;
        }
        catch (Exception ex)
        {
            await DisplayAlertAsync("Błąd połączenia", $"Nie udało się pobrać danych: {ex.Message}", "OK");
        }
    }

    private async void OnProductItemTapped(object sender, TappedEventArgs e)
    {
        if (sender is Border border && border.BindingContext is Product wybranyProdukt)
        {
       
            await Navigation.PushAsync(new ProductDetails(wybranyProdukt, _cache));
        }
    }

    private void OnRefreshViewRefreshing(object sender, EventArgs e)
    {

    }

    private void OnSearchBarTextChanged(object sender, TextChangedEventArgs e)
    {
        FilterAndDisplayProducts();
    }

    private void FilterAndDisplayProducts()
    {
        string query = ProductSearchBar.Text?.Trim().ToLower() ?? string.Empty;

        _displayedProducts.Clear();

        foreach (var product in _allProducts)
        {
            if (string.IsNullOrWhiteSpace(query) ||
                product.Title.ToLower().Contains(query) ||
                product.Category.ToLower().Contains(query))
            {
                _displayedProducts.Add(product);
            }
        }
    }

    private async void OnProfileIconClicked(object sender, EventArgs e)
    {
        if (sender is Button button)
        {
            await Shell.Current.GoToAsync("/account");
        }
    }

    private async void OnCartIconClicked(object sender, EventArgs e)
    {
        _cache.SetProducts(_displayedProducts.ToList());
        if (sender is Button button)
        {
            await Shell.Current.GoToAsync("/shopping-cart");
        }
    }
}