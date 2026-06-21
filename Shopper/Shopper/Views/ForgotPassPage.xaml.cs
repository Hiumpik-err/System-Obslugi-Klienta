using Shopper.Services;
using System.Text.RegularExpressions;

namespace Shopper.Views;

public partial class ForgotPassPage : ContentPage
{
	public ForgotPassPage()
	{
		InitializeComponent();
	}

    private async void OnSendResetLinkClicked(object sender, EventArgs e)
    {
        if (string.IsNullOrWhiteSpace(EmailEntry.Text))
        {
            await DisplayAlertAsync("Błąd", "Wprowadź swój adres e-mail.", "OK");
            return;
        }
        string email = EmailEntry.Text.Trim();

        var emailRegex = @"^[^@\s]+@[^@\s]+\.[^@\s]+$";
        if (!Regex.IsMatch(email, emailRegex))
        {
            await DisplayAlertAsync("Błąd", "Wprowadzony adres e-mail jest niepoprawny.", "OK");
            return;
        }
        var (message, success) = await DBRestService.RestartPassword(email);
        if (!success)
        {
            await DisplayAlertAsync("Błąd", message, "OK");
            return;
        }
        await DisplayAlertAsync("Sukces", message, "OK"); return;


    }

    private async void OnBackToLoginClicked(object sender, EventArgs e)
    {
        await Shell.Current.GoToAsync("login");
    }
}