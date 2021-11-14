using System.Collections.Generic;
using System.Text.Json;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;


namespace Color_it.game.coloring
{
    public class FillingMap
    {
        private Color[] _rowData;
        private int fragmentCount, width, height;
        private Dictionary<Color, List<FillingMapData.ColorPoints.Point>> _unfilledFragments = new();
        private FillingMapData _fillingMapData;

        /// <summary>
        /// Читает Json в объекты модели
        /// </summary>
        /// @details Принимает на вход строку следующего вида:
        /// {
        ///    "RowImage":"img/rowImage1.png",
        ///    "FinalImage":"img/finalImage1.png",
        ///    "CPoints":[
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
            _fillingMapData = JsonSerializer.Deserialize<FillingMapData>(fillingMapJsonString);
            Texture2D rowImage = null;//todo GameCore.Core.ResourceManager.GetObject(_fillingMapData.RowImage) as Texture2D;
            width = rowImage.Width;
            height = rowImage.Height;
            _rowData = new Color[width * height];
            rowImage.GetData(_rowData);
            fragmentCount = 0;
            foreach (FillingMapData.ColorPoints сPoint in _fillingMapData.СPoints)
            {
                _unfilledFragments.Add(new Color(сPoint.Color), сPoint.Points);
                fragmentCount += сPoint.Points.Capacity;
            }
            //todo протестировать данный класс
        }

        public Color[] RowData => _rowData;

        public int FragmentCount => fragmentCount;

        public int Width => width;

        public int Height => height;

        public Dictionary<Color, List<FillingMapData.ColorPoints.Point>> UnfilledFragments => _unfilledFragments;

        public FillingMapData FillingMD => _fillingMapData;

        public struct FillingMapData
        {
            public FillingMapData(string rowImage, string finalImage, List<ColorPoints> сPoints)
            {
                RowImage = rowImage;
                FinalImage = finalImage;
                СPoints = сPoints;
            }

            public string RowImage { get; }

            public string FinalImage { get; }

            public List<ColorPoints> СPoints { get; }
            
            public struct ColorPoints
            {
                public ColorPoints(uint color, List<Point> points)
                {
                    Color = color;
                    Points = points;
                }

                public uint Color { get; }

                public List<Point> Points { get; }
            
                public struct Point
                {
                    public Point(int x, int y)
                    {
                        X = x;
                        Y = y;
                    }

                    public int X { get; }

                    public int Y { get; }
                }
            }
        }
    }
}