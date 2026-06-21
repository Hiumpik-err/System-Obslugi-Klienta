
using Shopper.Models;
using Shopper.UsageClasses;
using System;
using System.Collections.Generic;
using System.Text;

namespace Shopper.Cache
{
    public class LocalCache : IDataCache
    {
        private readonly Dictionary<int, int> _cache = new Dictionary<int, int>(); //produkt id, count
        private readonly List<Product> products = new List<Product>();

        public void AddData(int key, int value = 1)
        {
            if(_cache.ContainsKey(key))
            {
                _cache[key] += value;
            }
            else
            {
                _cache[key] = value;
            }
        }

        public void ClearCache() => _cache.Clear();

        public void SubtractElement(int key)
        {
            if (_cache[key] > 1) _cache[key]--;
            else _cache.Remove(key);
        }

        public Dictionary<int, int> GetAllData() => _cache;

        public int GetData(int key) => _cache.TryGetValue(key, out int value) ? value : 0;

        public void SetProducts(List<Product> products) => this.products.AddRange(products);


        public List<Product> GetProducts() => products;
    }
}