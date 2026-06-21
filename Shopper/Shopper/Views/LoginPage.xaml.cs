using Shopper.Services;

namespace Shopper.Views;

public partial class LoginPage : ContentPage
{
	public LoginPage()
	{
		InitializeComponent();
	}

    private async void GoRegister(object sender, EventArgs e)
    {
        if (sender is Button button)
        {

            await Navigation.PushAsync(new RegisterAccountPage());
        }
    }

    private async void Login(object sender, EventArgs e)
    {
        string _email = email.Text;
        string _password = password.Text;

        try
        {
            LoadingOverlay.IsVisible = true;
            await Task.Yield();

            var res = await DBRestService.Login(_email, _password);
            if (res.Item2)
            {
                await Shell.Current.GoToAsync("list");
            }
            else
            {
                throw new Exception(res.Item1);
            }
        }
        catch (Exception ex)
        {
            await DisplayAlertAsync("ERROR", ex.Message, "OK");
        }
        finally
        {
            LoadingOverlay.IsVisible = false;
        }
    }

    private async void ForgotPassword(object sender, EventArgs e)
    {
        await Shell.Current.GoToAsync("forgot-password");
    }
}