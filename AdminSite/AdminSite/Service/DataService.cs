using AdminSite.Models;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;

namespace AdminSite.Service
{
    public static class DataService
    {
        public static ObservableCollection<SupportItem> Items { get; set; }
        // Słownik przechowujący przypisania: Data (np. "2026-06-25") -> Lista ID zgłoszeń
        public static Dictionary<string, List<string>> PlannedTasks { get; set; }

        static DataService()
        {
            Items = new ObservableCollection<SupportItem>
            {
                new SupportItem
                {
                    Id = "req-1",
                    Title = "Brak responsywności w menu bocznym",
                    Type = "error",
                    Category = "UI/UX",
                    Priority = "Wysoki",
                    User = "Jan Kowalski",
                    Date = "2026-06-20",
                    Desc = "Menu boczne na telefonach iPhone 13 nakłada się na główną treść uniemożliwiając nawigację.",
                    Solutions = new List<string> { "Dodaj klasę hidden md:block", "Przebuduj hamburger menu", "Użyj Tailwind custom breakpoints" },
                    IsResolved = false
                },
                new SupportItem
                {
                    Id = "req-2",
                    Title = "Przycisk 'Wyślij' nie reaguje przy wolnym łączu",
                    Type = "error",
                    Category = "Backend",
                    Priority = "Krytyczny",
                    User = "Marta Nowak",
                    Date = "2026-06-19",
                    Desc = "Brak wizualnego feedbacku przy kliknięciu przycisku wysyłania formularza kontaktowego powoduje dublowanie zgłoszeń.",
                    Solutions = new List<string> { "Dodaj stan loading i wyłącz przycisk", "Dodaj spinner animowany", "Dodaj debounce" },
                    IsResolved = false
                },
                new SupportItem
                {
                    Id = "req-3",
                    Title = "Sugestia dodania ciemnego motywu do panelu",
                    Type = "suggestion",
                    Category = "Funkcjonalność",
                    Priority = "Niski",
                    User = "Kamil Cloud",
                    Date = "2026-06-18",
                    Desc = "Klienci pracujący w nocy bardzo proszą o ciemny motyw interfejsu (dark mode), aby oszczędzać wzrok.",
                    Solutions = new List<string> { "Zaimplementuj klasę dark w Tailwind CSS", "Dodaj przycisk przełączania motywu" },
                    IsResolved = false
                },
                new SupportItem
                {
                    Id = "req-4",
                    Title = "Błędne formatowanie waluty w podsumowaniu faktury",
                    Type = "error",
                    Category = "Finanse",
                    Priority = "Wysoki",
                    User = "Piotr Wiśniewski",
                    Date = "2026-06-17",
                    Desc = "System pokazuje kwoty jako np. 120.5 PLN zamiast eleganckiego 120,50 zł.",
                    Solutions = new List<string> { "Użyj Intl.NumberFormat", "Stwórz helper formatujący waluty" },
                    IsResolved = false
                }
            };

            PlannedTasks = new Dictionary<string, List<string>>
            {
                { "2026-06-25", new List<string> { "req-1" } },
                { "2026-06-28", new List<string> { "req-3" } }
            };
        }

        public static void AssignTaskToDate(string taskId, string dateStr)
        {
            // Usuń z dotychczasowych dni
            foreach (var key in PlannedTasks.Keys.ToList())
            {
                PlannedTasks[key].Remove(taskId);
                if (PlannedTasks[key].Count == 0)
                {
                    PlannedTasks.Remove(key);
                }
            }

            // Dodaj do nowego dnia
            if (!PlannedTasks.ContainsKey(dateStr))
            {
                PlannedTasks[dateStr] = new List<string>();
            }
            if (!PlannedTasks[dateStr].Contains(taskId))
            {
                PlannedTasks[dateStr].Add(taskId);
            }
        }
    }
}
