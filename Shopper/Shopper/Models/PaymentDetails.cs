using System;
using System.Collections.Generic;
using System.Text;

namespace Shopper.Models
{
    public class PaymentDetails
    {
        public String paymentAccomplishedDate { get; set; }
        public string paymentType { get; set; }
        public bool isPaid { get; set; }
        public Orders orderId { get; set; }

        public PaymentDetails(string paymentType, String paymentDate, Orders order)
        {
            this.paymentType = paymentType;
            this.paymentAccomplishedDate = paymentDate;
            this.isPaid = true; // Assuming payment is successful for simplicity
            this.orderId = order;
        }
    }
}
