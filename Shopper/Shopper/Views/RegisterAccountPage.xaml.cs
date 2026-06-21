using Shopper.Models;
using Shopper.Services;
using System.Net;
using System.Net.Http.Json;

namespace Shopper.Views;

public partial class RegisterAccountPage : ContentPage
{
	public RegisterAccountPage()
	{
		InitializeComponent();
	}

    private async void GoLogin(object sender, EventArgs e)
    {
        if (sender is Button button )
        {

            await Navigation.PushAsync(new LoginPage());
        }
    }

    private async void Register(object sender, EventArgs e)
    {
        if(String.IsNullOrEmpty(fname.Text) || String.IsNullOrEmpty(lname.Text) || String.IsNullOrEmpty(email.Text) || String.IsNullOrEmpty(password.Text))
        {
            await DisplayAlertAsync("ERROR", "Proszę wypełnić wszystkie pola.", "OK");
            return;
        }
        Account newAccount = new Account(fname.Text, lname.Text, email.Text, password.Text);
        try
        {
            RegisterLoadingOverlay.IsVisible = true;
            await Task.Yield();

            (string, bool) res = await DBRestService.RegisterUser(newAccount);
            if (res.Item2)
            {
                await Shell.Current.GoToAsync("login");
            }
            else
            {
                throw new Exception(string.IsNullOrWhiteSpace(res.Item1)
                    ? "Rejestracja nie powiodła się. Spróbuj ponownie."
                    : res.Item1);
            }
        }
        catch (Exception ex)
        {
            await DisplayAlertAsync("ERROR", ex.Message, "OK");
        }
        finally
        {
            RegisterLoadingOverlay.IsVisible = false;
        }
       

    }
}