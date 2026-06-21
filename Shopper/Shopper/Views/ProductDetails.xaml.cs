using Shopper.Cache;
using Shopper.Models;
using Shopper.UsageClasses;

namespace Shopper.Views;

public partial class ProductDetails : ContentPage
{
    private readonly IDataCache _cartCache;
    public Product SelectedProduct { get; set; }
	public ProductDetails(Product product, IDataCache cartCache)
	{
		InitializeComponent();
        SelectedProduct = product;
        _cartCache = cartCache;
        if (SelectedProduct != null)
        {
            
            Title = SelectedProduct.Title;
            ProductImage.Source = SelectedProduct.Image;
            TitleLabel.Text = SelectedProduct.Title;
            CategoryLabel.Text = SelectedProduct.Category.ToUpper();
            PriceLabel.Text = $"{SelectedProduct.Price:F2} PLN";
            DescriptionLabel.Text = SelectedProduct.Description;
        }
    }

    private void AddToCart(object sender, EventArgs e) => _cartCache.AddData(SelectedProduct.Id);

}