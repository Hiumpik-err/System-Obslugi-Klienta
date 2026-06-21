using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;
using System.Text.Json.Serialization;

namespace Shopper.Models
{
    public class DeliveryDetails
    {
        public int? deliveryDetailsId { get; set; }
        public string region { get; set; }
        public string city { get; set; }
        public string street { get; set; }
        public string townCode { get; set; }
        public string homeNumber { get; set; }
        public string deliveredDate { get; set; }
        public double deliveryCost { get; set; } = 14.99;
        public Account userId { get; set; }
        public Orders orderId { get; set; }

        [JsonConstructor]
        public DeliveryDetails() { }

        public DeliveryDetails(string region, string city, string street, string townCode, string homeNumber, Account userId, Orders order)
        {
            this.region = region;
            this.city = city;
            this.street = street;
            this.townCode = townCode;
            this.homeNumber = homeNumber;
            this.userId = userId;
            this.orderId = order;
            deliveredDate = (DateTime.Now + TimeSpan.FromDays(7)).ToString("yyyy-MM-dd");
        }
    }
}
