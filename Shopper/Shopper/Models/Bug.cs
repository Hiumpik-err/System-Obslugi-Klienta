using System;
using System.Collections.Generic;
using System.Text;

namespace Shopper.Models
{
    public class Bug
    {
        public string bugName { get; set; }
        public string bugDescription { get; set; }
        public string reportDate { get; set; }

        public Bug() { }
        public Bug(string bugName, string bugDescription)
        {
            this.bugName = bugName;
            this.bugDescription = bugDescription;
            this.reportDate = DateTime.Now.ToString("yyyy-MM-dd");
        }
    }
}
