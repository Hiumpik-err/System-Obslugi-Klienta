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
    /// Logika interakcji dla klasy PlannerWindow.xaml
    /// </summary>
    public partial class PlannerWindow : Window
    {
        private DateTime _currentMonth;

        public PlannerWindow()
        {
            InitializeComponent();
            _currentMonth = new DateTime(2026, 6, 1); // Zaczynamy od czerwca 2026 zgodnie z pierwowzorem
            RenderCalendar();
            LoadUnplannedTasks();
        }

        private void LoadUnplannedTasks()
        {
            // Zgłoszenia, które nie są jeszcze zaplanowane na żaden dzień
            var plannedIds = DataService.PlannedTasks.Values.SelectMany(x => x).ToList();
            var unplanned = DataService.Items.Where(i => !plannedIds.Contains(i.Id)).ToList();
            UnplannedListBox.ItemsSource = unplanned;
        }

        private void RenderCalendar()
        {
            CalendarGrid.Children.Clear();
            TxtMonthYear.Text = $"{_currentMonth.ToString("MMMM yyyy")}";

            int year = _currentMonth.Year;
            int month = _currentMonth.Month;

            DateTime firstDayOfMonth = new DateTime(year, month, 1);
            int daysInMonth = DateTime.DaysInMonth(year, month);

            // Pobieramy dzień tygodnia dla pierwszego dnia (0 = Poniedziałek, ..., 6 = Niedziela)
            int dayOfWeekVal = ((int)firstDayOfMonth.DayOfWeek + 6) % 7;

            // Dodaj puste pola z poprzedniego miesiąca
            for (int i = 0; i < dayOfWeekVal; i++)
            {
                CalendarGrid.Children.Add(new Border { Background = new SolidColorBrush(Color.FromRgb(248, 250, 252)) });
            }

            // Renderuj dni miesiąca
            for (int day = 1; day <= daysInMonth; day++)
            {
                string currentDateStr = $"{year}-{month:D2}-{day:D2}";

                // Tworzymy pojedynczą komórkę dnia kalendarzowego
                Border dayCell = new Border
                {
                    Background = new SolidColorBrush(Color.FromRgb(255, 255, 255)),
                    BorderBrush = new SolidColorBrush(Color.FromRgb(226, 232, 240)),
                    BorderThickness = new Thickness(1),
                    CornerRadius = new CornerRadius(8),
                    Margin = new Thickness(2),
                    AllowDrop = true,
                    Tag = currentDateStr
                };

                // Zdarzenia Drag & Drop na komórce
                dayCell.DragOver += DayCell_DragOver;
                dayCell.Drop += DayCell_Drop;

                StackPanel cellStack = new StackPanel { Margin = new Thickness(5) };

                // Wyświetlanie numeru dnia
                TextBlock dayNumber = new TextBlock
                {
                    Text = day.ToString(),
                    FontWeight = FontWeights.Bold,
                    FontSize = 12,
                    Foreground = new SolidColorBrush(Color.FromRgb(30, 41, 59)),
                    Margin = new Thickness(0, 0, 0, 5)
                };
                cellStack.Children.Add(dayNumber);

                // Pobieranie i dodawanie zaplanowanych zadań dla tego dnia
                if (DataService.PlannedTasks.ContainsKey(currentDateStr))
                {
                    foreach (var taskId in DataService.PlannedTasks[currentDateStr])
                    {
                        var task = DataService.Items.FirstOrDefault(t => t.Id == taskId);
                        if (task != null)
                        {
                            Border taskBadge = new Border
                            {
                                Background = task.Type == "error" ? new SolidColorBrush(Color.FromRgb(254, 226, 226)) : new SolidColorBrush(Color.FromRgb(209, 250, 229)),
                                CornerRadius = new CornerRadius(4),
                                Padding = new Thickness(4, 2, 4, 2),
                                Margin = new Thickness(0, 2, 0, 2),
                                Cursor = Cursors.Hand
                            };
                            taskBadge.MouseDown += (s, e) => {
                                DetailWindow dw = new DetailWindow(taskId);
                                dw.Show();
                            };

                            TextBlock taskText = new TextBlock
                            {
                                Text = task.Title,
                                FontSize = 10,
                                Foreground = task.Type == "error" ? new SolidColorBrush(Color.FromRgb(185, 28, 28)) : new SolidColorBrush(Color.FromRgb(4, 120, 87)),
                                TextTrimming = TextTrimming.CharacterEllipsis
                            };
                            taskBadge.Child = taskText;
                            cellStack.Children.Add(taskBadge);
                        }
                    }
                }

                dayCell.Child = cellStack;
                CalendarGrid.Children.Add(dayCell);
            }
        }

        // Aktywacja mechanizmu przeciągania na listboxie
        private void UnplannedListBox_PreviewMouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            ListBox parent = sender as ListBox;
            var item = parent?.SelectedItem as SupportItem;

            if (item != null)
            {
                DragDrop.DoDragDrop(parent, item, DragDropEffects.Move);
            }
        }

        private void DayCell_DragOver(object sender, DragEventArgs e)
        {
            e.Effects = DragDropEffects.Move;
            e.Handled = true;
        }

        private void DayCell_Drop(object sender, DragEventArgs e)
        {
            Border cell = sender as Border;
            if (cell != null && cell.Tag != null)
            {
                string targetDate = cell.Tag.ToString();

                // Pobieramy przeciągnięty obiekt
                if (e.Data.GetDataPresent(typeof(SupportItem)))
                {
                    SupportItem droppedItem = e.Data.GetData(typeof(SupportItem)) as SupportItem;
                    if (droppedItem != null)
                    {
                        DataService.AssignTaskToDate(droppedItem.Id, targetDate);

                        // Odświeżenie interfejsu
                        RenderCalendar();
                        LoadUnplannedTasks();
                    }
                }
            }
        }

        private void PrevMonth_Click(object sender, RoutedEventArgs e)
        {
            _currentMonth = _currentMonth.AddMonths(-1);
            RenderCalendar();
        }

        private void NextMonth_Click(object sender, RoutedEventArgs e)
        {
            _currentMonth = _currentMonth.AddMonths(1);
            RenderCalendar();
        }

        private void Back_Click(object sender, RoutedEventArgs e)
        {
            MainWindow mw = new MainWindow();
            mw.Show();
            this.Close();
        }
    }
}
