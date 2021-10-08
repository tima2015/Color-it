using System.Collections.Generic;
using System.Text.Json;
using System.Text.Json.Serialization;
using Microsoft.Xna.Framework;

namespace Color_it.game.coloring
{
    public class FillingMap
    {
        private string rowImage, finalImage;
        private Color[] _rowData;
        private int fragmentCount, width, height;
        private Dictionary<Color, Stack<Point>> _unfilledFragments;

        /// <summary>
        /// Читает Json в объекты модели
        /// </summary>
        /// @details Принимает на вход строку следующего вида:
        /// {
        ///    "RowImage":"img/rowImage1.png",
        ///    "FinalImage":"img/finalImage1.png",
        ///    "ColorPoints":[
        ///       {
        ///          Color:4278245375U,
        ///          Points:[
        ///             {
        ///                "X":2,
        ///                "Y":4
        ///             }
        ///          ]
        ///       }
        ///    ]
        /// }
        /// <param name="fillingMapJsonString"></param>
        public FillingMap(string fillingMapJsonString)
        {
            FillingMapData fillingMapData = JsonSerializer.Deserialize<FillingMapData>(fillingMapJsonString);
            //todo реализовать и протестировать данный класс
        }

        public struct Point
        {
            public int X { get; set; }

            public int Y { get; set; }
        }
        
        public struct ColorPoints
        {
            public uint Color { get; set; }

            public List<Point> Points { get; set; }
        }
        
        public struct FillingMapData
        {
            public string RowImage { get; set; }

            public string FinalImage { get; set; }

            public List<ColorPoints> ColorPoints { get; set; }
        }
    }
}