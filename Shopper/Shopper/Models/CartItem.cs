using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Text;

namespace Shopper.Models
{
    public class CartItem : INotifyPropertyChanged
    {
        private int _quantity;
        private Product _product;

        public Product Product
        {
            get => _product;
            set { _product = value; OnPropertyChanged(); OnPropertyChanged(nameof(TotalPrice)); }
        }

        public int Quantity
        {
            get => _quantity;
            set
            {
                if (_quantity != value)
                {
                    _quantity = value;
                    OnPropertyChanged();
                    OnPropertyChanged(nameof(TotalPrice));
                }
            }
        }

        // Cena przeliczona: cena jednostkowa * ilość
        public decimal TotalPrice => Product != null ? Product.Price * Quantity : 0;

        public event PropertyChangedEventHandler PropertyChanged;
        protected void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
