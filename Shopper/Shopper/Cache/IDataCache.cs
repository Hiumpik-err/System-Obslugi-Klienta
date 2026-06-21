using Shopper.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace Shopper.UsageClasses
{
    public interface IDataCache
    {
        void AddData(int key, int value = 1);
        void SubtractElement(int id);
        void ClearCache();
        int GetData(int key);
        Dictionary<int, int> GetAllData();
        void SetProducts(List<Product> products);
        List<Product> GetProducts();
    }
}
