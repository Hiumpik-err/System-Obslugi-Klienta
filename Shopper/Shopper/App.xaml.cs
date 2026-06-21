using Microsoft.Extensions.DependencyInjection;
using Shopper.Services;

namespace Shopper
{
    public partial class App : Application
    {
        public App()
        {
            InitializeComponent();            
        }
        protected async override void OnStart()
        {
            base.OnStart();
            await DBRestService.Logout();
        }
        protected override Window CreateWindow(IActivationState? activationState)
        {
            return new Window(new AppShell());
        }

    }
}