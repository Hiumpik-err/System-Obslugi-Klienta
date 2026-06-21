using AdminSite.Models;
using AdminSite.Service;
using AdminSite.Views;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace AdminSite
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();

            // Podpięcie danych do list
            NewRemarksItemsControl.ItemsSource = DataService.Items.Take(3).ToList();
            AllItemsGrid.ItemsSource = DataService.Items;

            Loaded += MainWindow_Loaded;
        }
        private void MainWindow_Loaded(object sender, RoutedEventArgs e)
        {
            // Animacja powitalna: Wjazd tekstu z lewej strony
            DoubleAnimation slideAnim = new DoubleAnimation
            {
                From = -500,
                To = 50,
                Duration = TimeSpan.FromSeconds(1.2),
                EasingFunction = new QuadraticEase { EasingMode = EasingMode.EaseOut }
            };
            WelcomePanel.BeginAnimation(Canvas.LeftProperty, slideAnim);
        }

        private void MainScrollViewer_ScrollChanged(object sender, ScrollChangedEventArgs e)
        {
            double offset = MainScrollViewer.VerticalOffset;

            // Efekt Paralaksy: Przesuwanie pigułek z różnymi współczynnikami prędkości (Y-Offset)
            Canvas.SetTop(PillCoral, 100 - (offset * 0.3));
            Canvas.SetTop(PillPink, 180 - (offset * 0.5));
            Canvas.SetTop(PillWhite, 350 - (offset * 0.2));
            Canvas.SetTop(PillBlue, 80 - (offset * 0.45));
            Canvas.SetTop(PillPurple, 380 - (offset * 0.6));

            // Pokazywanie Toasta ("Zaplanuj wykonanie"), gdy sekcja tabeli "Wszystkie" wchodzi w pole widzenia
            GeneralTransform transform = AllSection.TransformToAncestor(this);
            Point relativePoint = transform.Transform(new Point(0, 0));

            if (relativePoint.Y < (this.Height / 2) && relativePoint.Y > 0)
            {
                if (ToastNotification.Visibility == Visibility.Collapsed)
                {
                    ToastNotification.Visibility = Visibility.Visible;
                    // Prosta animacja wjazdu powiadomienia od dołu
                    DoubleAnimation slideUp = new DoubleAnimation
                    {
                        From = 100,
                        To = 0,
                        Duration = TimeSpan.FromSeconds(0.4),
                        EasingFunction = new QuadraticEase { EasingMode = EasingMode.EaseOut }
                    };
                    ToastTranslate.BeginAnimation(TranslateTransform.YProperty, slideUp);
                }
            }
        }

        private void OpenPlanner_Click(object sender, RoutedEventArgs e)
        {
            PlannerWindow planner = new PlannerWindow();
            planner.Show();
            this.Close(); // Zamknięcie okna głównego (przełączenie podstrony)
        }

        private void Card_MouseDown(object sender, MouseButtonEventArgs e)
        {
            var border = sender as Border;
            if (border != null && border.DataContext is SupportItem item)
            {
                OpenDetailWindow(item.Id);
            }
        }

        private void AllItemsGrid_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            if (AllItemsGrid.SelectedItem is SupportItem selectedItem)
            {
                OpenDetailWindow(selectedItem.Id);
            }
        }

        private void OpenDetailWindow(string id)
        {
            DetailWindow details = new DetailWindow(id);
            details.Show();
        }

        private void CloseToast_Click(object sender, RoutedEventArgs e)
        {
            ToastNotification.Visibility = Visibility.Collapsed;
        }
    }
}