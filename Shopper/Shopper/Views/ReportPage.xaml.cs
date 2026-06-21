using Shopper.Models;
using Shopper.Services;

namespace Shopper.Views;

public partial class ReportPage : ContentPage
{
	public ReportPage()
	{
		InitializeComponent();
	}

    protected override async void OnAppearing()
    {
        base.OnAppearing();
/*        if (await SecureStorage.GetAsync("currentUser") is null)
        {
            await DisplayAlertAsync("Warning", "Zaloguj się, aby móc zgłosić błąd!", "OK");
            await Shell.Current.GoToAsync("/login");
            return;
        }*/
    }

    private async void OnSubmitBugClicked(object sender, EventArgs e)
    {


        if(string.IsNullOrWhiteSpace(BugTitleEntry.Text) || string.IsNullOrWhiteSpace(BugDescriptionEditor.Text))
        {
            await DisplayAlertAsync("WARNING", "Wypisz co sie stało, pustych zgłoszeń nie przyjmujemy!", "OK");
            return;
        }

        var bug = new Bug(BugTitleEntry.Text.Trim(), BugDescriptionEditor.Text.Trim());

        var result = await ProductsRestService.SendNewBug(bug);
        
        if(result.Item2)
        {
            await DisplayAlertAsync("SUCCESS", result.Item1, "OK");
        }
        else
        {
            await DisplayAlertAsync("ERROR", result.Item1, "OK");
            return;
        }


        await Shell.Current.GoToAsync("/list");
    }
}