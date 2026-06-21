using System;
using System.Collections.Generic;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace Shopper.Models
{
    public class Account
    {
        //[JsonIgnore(Condition = JsonIgnoreCondition.WhenWriting)]
        public int? userId { get; set; }
        [JsonIgnore]
        public string fullName { get => $"{firstName} {lastName}"; }
        public string firstName { get; set; }
        public string lastName { get; set; }
        public string email { get; set; }
        public string password { get; set; }
        public string accountCreationDate { get; set; }
        [JsonIgnore]
        public DeliveryDetails? deliveryDetails { get; set; }


        [JsonPropertyName("deliveryDetails")]
        public object? RawDeliveryDetails
        {
            get => deliveryDetails;
            set
            {
                if (value is string || value == null || value.ToString() == "")
                {
                    deliveryDetails = null;
                }
                else if (value is JsonElement element)
                {
                    var rawJson = element.GetRawText();
                    deliveryDetails = JsonSerializer.Deserialize<DeliveryDetails>(rawJson);
                }
            }
        }

        public Account() { }
        public Account(string fname, string lname, string email, string password)
        {
            this.firstName = fname;
            this.lastName = lname;
            this.email = email;
            this.password = password;
        }

    }
}
