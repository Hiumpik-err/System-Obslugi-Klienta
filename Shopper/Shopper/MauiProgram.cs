using Microsoft.Extensions.Logging;
using Shopper.Cache;
using Shopper.Models;
using Shopper.UsageClasses;
using Shopper.Views;

namespace Shopper
{
    public static class MauiProgram
    {
        public static MauiApp CreateMauiApp()
        {
            var builder = MauiApp.CreateBuilder();
            builder
                .UseMauiApp<App>()
                .ConfigureFonts(fonts =>
                {
                    fonts.AddFont("OpenSans-Regular.ttf", "OpenSansRegular");
                    fonts.AddFont("OpenSans-Semibold.ttf", "OpenSansSemibold");
                });

#if DEBUG
    		builder.Logging.AddDebug();
#endif
            builder.Services.AddSingleton<IDataCache, LocalCache>();

            return builder.Build();
        }
    }
}
