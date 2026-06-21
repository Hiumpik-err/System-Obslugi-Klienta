using AdminSite.Models;
using AdminSite.Service;
using System;
using System.Collections.Generic;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace AdminSite.Views
{
    /// <summary>
    /// Logika interakcji dla klasy DetailWindow.xaml
    /// </summary>
    public partial class DetailWindow : Window
    {
        private SupportItem _item;
        public DetailWindow(string itemId)
        {
            InitializeComponent();
            _item = DataService.Items.FirstOrDefault(i => i.Id == itemId);

            if (_item != null)
            {
                TxtId.Text = $"ID: {_item.Id}";
                TxtTitle.Text = _item.Title;
                TxtDesc.Text = _item.Desc;
                TxtPriority.Text = _item.Priority;
                TxtCategory.Text = $"Kategoria: {_item.Category} | Zgłosił: {_item.User}";
                SolutionsList.ItemsSource = _item.Solutions;

                if (_item.IsResolved)
                {
                    BtnResolve.IsEnabled = false;
                    BtnResolve.Content = "Rozwiązano";
                    BtnResolve.Background = new SolidColorBrush(Colors.Gray);
                }
            }
        }
        private void Resolve_Click(object sender, RoutedEventArgs e)
        {
            if (_item != null)
            {
                _item.IsResolved = true;
                BtnResolve.IsEnabled = false;
                BtnResolve.Content = "Rozwiązano";
                BtnResolve.Background = new SolidColorBrush(Colors.Gray);
                MessageBox.Show("Zgłoszenie pomyślnie zaktualizowane jako wykonane!", "Status operacji", MessageBoxButton.OK, MessageBoxImage.Information);
            }
        }

        private void Close_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }
    }
}
