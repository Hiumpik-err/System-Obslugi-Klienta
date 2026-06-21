using System;
using System.Collections.Generic;
using System.Text;

namespace AdminSite.Models
{
    public class SupportItem
    {
        public string Id { get; set; }
        public string Title { get; set; }
        public string Type { get; set; } 
        public string Category { get; set; }
        public string Priority { get; set; }
        public string User { get; set; }
        public string Date { get; set; }
        public string Desc { get; set; }
        public List<string> Solutions { get; set; }
        public bool IsResolved { get; set; }
    }
}
