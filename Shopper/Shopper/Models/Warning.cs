using Java.Time;
using System;
using System.Collections.Generic;
using System.Text;

namespace Shopper.Models
{
    public class Warning
    {
        public int? warningId {  get; set; }
        public string issueTopic { get; set; }
        public string issueStatus { get; set; }
        public string recivedDate { get; set; }
        public string affectedProducts  { get; set; }
        public string description { get; set; }
        public string expectations { get; set; }
        public Account userId { get; set; }
        public Orders orderId { get; set; }

        public Warning() { }
        public Warning(string issueTopic, string issueStatus, string recivedDate, string affectedProducts, string description, string expectations, Account userId, Orders orderId)
        {
            this.issueTopic = issueTopic;
            this.issueStatus = issueStatus;
            this.recivedDate = recivedDate;
            this.affectedProducts = affectedProducts;
            this.description = description;
            this.expectations = expectations;
            this.orderId = orderId;
            this.userId = userId;
        }
    }
}
