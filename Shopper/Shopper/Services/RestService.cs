using Shopper.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Text.Json;

namespace Shopper.Services
{
    public class RestService
    {
        private readonly HttpClient _client;
        private readonly JsonSerializerOptions _options;
        private readonly string _url = "https://fakestoreapi.com/products";

        public RestService()
        {
            _client = new HttpClient(new HttpClientHandler
            {
                ServerCertificateCustomValidationCallback = HttpClientHandler.DangerousAcceptAnyServerCertificateValidator
            });

            _options = new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true
            };
        }

        public async Task<List<Product>> GetProductsAsync()
        {
            var resp = await _client.GetAsync(_url);
            if (resp.IsSuccessStatusCode)
            {
                var json = await resp.Content.ReadAsStringAsync();
                return JsonSerializer.Deserialize<List<Product>>(json, _options);
            }

            return new List<Product>();
        }

        public async Task<Product> GetProductAsync(int id)
        {
            var resp = await _client.GetAsync($"{_url}/{id}");
            if (resp.IsSuccessStatusCode)
            {
                var json = await resp.Content.ReadAsStringAsync();
                return JsonSerializer.Deserialize<Product>(json, _options);
            }
            return null;
        }
    }
}
