using Shopper.Models;
using Shopper.Services;
using System.Text.Json;
using System.Text.RegularExpressions;

namespace Shopper.Views;

public partial class AccountPage : ContentPage
{
	public AccountPage()
	{
		InitializeComponent();
	}
    protected async override void OnAppearing()
    {
        base.OnAppearing();
        LoadUserData();

    }
    private async void LoadUserData()
    {
        var json = await SecureStorage.Default.GetAsync("currentUser");
        if (json is not null)
        {
            Account currentUser = JsonSerializer.Deserialize<Account>(json);
            BindingContext = currentUser;
        }
        else
        {
            await Shell.Current.GoToAsync("/login");
        }
    }

    private async void UpdateUserData(object sender, EventArgs e)
    {
        var user = BindingContext as Account;
        var updatedUser = await DBRestService.UpdateUser(user.userId, user);
        LoadUserData();
    }

    private async void LogoutUser(object sender, EventArgs e)
    {
        await DBRestService.Logout();

        await Shell.Current.GoToAsync("/login");
    }

    private async void DeleteUser(object sender, EventArgs e)
    {
        var user = BindingContext as Account;
        var res = await DBRestService.DeleteUser(user.userId);
        if (res.Item2)
        {
            await DisplayAlertAsync(res.Item1, "Account has been deleted successfully", "OK");
        }
        else
        {
            await DisplayAlertAsync("ERROR", res.Item1, "OK");
        }
        await Shell.Current.GoToAsync("login");

    }
}