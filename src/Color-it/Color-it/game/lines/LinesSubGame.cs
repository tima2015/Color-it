using System;
using System.Collections.Generic;
using Color_it.game.coloring;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using static Color_it.game.coloring.GameEventListener;

namespace Color_it.game.lines
{
    
    /// <summary>
    ///     Класс реализующий мини игру "Линии"
    /// </summary>
    public class LinesSubGame : SubGame
    {
        public LinesSubGame(GameEventListener listener) :
            base(new LinesModel(), new LinesView(), new LinesController(listener))
        {
        }

        /// <summary>
        ///     Реализация модели мини игры.
        /// </summary>
        private class LinesModel : IModel
        {
            private const int fieldSize = 9;
            private const int cellSize = 40;
            private int fieldX, fieldY; 
            private LinesCell[,] cells;
            private LinesCell[] nextCells;

            public LinesModel()
            {
                cells = new LinesCell[fieldSize, fieldSize];
                nextCells = new LinesCell[3];
                fieldX = GameCore.Core.SubGameViewport.Width / 2 - 5 * cellSize;
                fieldX = GameCore.Core.SubGameViewport.Height / 2 - 5 * cellSize;
            }
            public LinesCell[,] Cells { get { return cells; } }
            public LinesCell[] NextCells { get { return nextCells; } }
            public int FieldSize { get { return fieldSize; } }
            public int CellSize { get { return cellSize; } }
            public int FieldX { get { return fieldX; } }
            public int FieldY { get { return fieldY; } }
        }

            /// <summary>
            ///     Реализация отображения мини игры.
            /// </summary>
            private class LinesView : IView
        {
            public void Draw(SpriteBatch batch)
            {
                throw new NotImplementedException();
            }
        }

        /// <summary>
        ///     Реализация контроллера мини игры.
        /// </summary>
        private class LinesController : IController
        {
            //при выполнии условия закрашивания вызывайте listener.Notify(new PaintingEvent(count, color))
            //для подробностей смотрите GameEventListenr.cs
            private GameEventListener _listener;
            private LinesCell choosedCell;
            private int choosedCellX, choosedCellY;
            private int[] orbs = new int[3];
            private LinesModel model;

            public LinesController(GameEventListener listener)
            {
                _listener = listener;
                model = new LinesModel();
            }

            public void Update(float delta)
            {
                CellSearch();                
            }

            public void OnBegin()
            {
                choosedCell = new LinesCell();
                var rand = new Random();
                for (int i = 0; i < 3; i++)
                {
                    model.NextCells[i] = new LinesCell();
                    model.NextCells[i].TextureNumber = rand.Next(1, 4);
                }
                while (true)
                {
                    for (int i = 0; i < 3; i++)
                    {
                        orbs[i] = rand.Next(0, 81);
                    }
                    if (!(orbs[0] == orbs[1] || orbs[0] == orbs[2]
                        || orbs[1] == orbs[2]))
                        break;
                }

                for (int i = 0; i < model.FieldSize; i++)
                {
                    for (int j = 0; j < model.FieldSize; j++)
                    {
                        model.Cells[i, j] = new LinesCell();
                        for (int k = 0; k < 3; k++)
                        {
                            if (orbs[k] / model.FieldSize == i && orbs[k] % model.FieldSize == j)
                            {
                                model.Cells[i, j].TextureNumber = model.NextCells[k].TextureNumber;
                                break;
                            }
                        }
                    }
                }
                for (int i = 0; i < 3; i++)
                {
                    model.NextCells[i].TextureNumber = rand.Next(1, 4);
                }
            }

            public void OnResume()
            {
                throw new NotImplementedException();
            }

            public void OnPause()
            {
                throw new NotImplementedException();
            }

            public void OnEnd()
            {
                throw new NotImplementedException();
            }

            /// <summary>
            ///     Удаление закрашенных линии
            ///     Возвращает кол-во освободившихся клеток
            /// </summary>
            private int DeleteLines()
            {
                int del = 0;
                for (int i = 0; i < model.FieldSize; i++)
                {
                    for (int j = 0; j < model.FieldSize; j++)
                    {
                        if (model.Cells[i, j].Visited)
                        {
                            model.Cells[i, j].Visited = false;
                            model.Cells[i, j].TextureNumber = 0;
                            del++;
                        }
                    }
                }
                return del;
            }
            /// <summary>
            ///     Выбор 3-х случайных клеток для вставки шаров
            /// </summary>
            private void InsertOrbs()
            {
                var rand = new Random();
                //Ищем 3 пустые клетки
                while (true)
                {
                    for (int i = 0; i < 3; i++)
                    {
                        orbs[i] = rand.Next(0, 81);
                    }
                    if (!(orbs[0] == orbs[1] || orbs[0] == orbs[2] || orbs[1] == orbs[2]))
                        if (!(model.Cells[orbs[0] / model.FieldSize, orbs[0] % model.FieldSize].TextureNumber > 0
                            || model.Cells[orbs[1] / model.FieldSize, orbs[1] % model.FieldSize].TextureNumber > 0
                            || model.Cells[orbs[2] / model.FieldSize, orbs[2] % model.FieldSize].TextureNumber > 0))
                            break;
                }
                for (int i = 0; i < model.FieldSize; i++)
                {
                    for (int j = 0; j < model.FieldSize; j++)
                    {
                        for (int k = 0; k < 3; k++)
                        {
                            if (i == orbs[k] / model.FieldSize && j == orbs[k] % model.FieldSize)
                            {
                                model.Cells[i, j].TextureNumber = model.NextCells[k].TextureNumber;
                                break;
                            }
                        }
                    }
                }
                //Выбор 3-х следующих шаров для вставки
                for (int i = 0; i < 3; i++)
                {
                    model.NextCells[i].TextureNumber = rand.Next(1, 5);
                }
            }
            /// <summary>
            ///     Снятие всех меток Visited
            /// </summary>
            private void UnmarkCells()
            {
                foreach (LinesCell c in model.Cells)
                {
                    c.Visited = false;
                }
            }

            /// <summary>
            ///     Поиск пути от start до end
            ///     Реализовано с помощью алгоритма BFS
            /// </summary>
            /// <param name="start_x">Координаты строки стартовой ячейки</param>
            /// <param name="start_y">Координаты столбца стартовой ячейки</param>
            /// <param name="end_x">Координаты строки конечной ячейки</param>
            /// <param name="end_y">Координаты столбца конечной ячейки</param>
            private bool Movable(int start_x, int start_y, int end_x, int end_y)
            {
                Queue<LinesCell> queue = new Queue<LinesCell>();
                queue.Enqueue(model.Cells[start_x, start_y]);
                int cur_x = start_x, cur_y = start_y;

                while (queue.Count > 0)
                {
                    LinesCell cur = queue.Dequeue();
                    if (cur_x == end_x && cur_y == end_y)
                    {
                        UnmarkCells();
                        return true;
                    }
                    cur.Visited = true;

                    //проверка слева от текущей позиции
                    if (cur_x > 0 && !(model.Cells[cur_x - 1, cur_y].TextureNumber > 0))
                    {
                        if (!(model.Cells[cur_x - 1, cur_y].Visited))
                        {
                            queue.Enqueue(model.Cells[cur_x - 1, cur_y]);
                            model.Cells[cur_x - 1, cur_y].Visited = true;
                            if (cur_x  == end_x && cur_y == end_y)
                            {
                                UnmarkCells();
                                return true;
                            }
                        }
                    }

                    //проверка справа от текущей позиции
                    if (cur_x < model.FieldSize - 1 && !(model.Cells[cur_x + 1, cur_y].TextureNumber > 0))
                    {
                        if (!(model.Cells[cur_x + 1, cur_y].Visited))
                        {
                            queue.Enqueue(model.Cells[cur_x + 1, cur_y]);
                            model.Cells[cur_x + 1, cur_y].Visited = true;
                            if (cur_x + 1 == end_x && cur_y == end_y)
                            {
                                UnmarkCells();
                                return true;
                            }
                        }
                    }

                    //проверка сверху от текущей позиции
                    if (cur_y > 0 && !(model.Cells[cur_x, cur_y - 1].TextureNumber > 0))
                    {
                        if (!(model.Cells[cur_x, cur_y - 1].Visited))
                        {
                            queue.Enqueue(model.Cells[cur_x, cur_y - 1]);
                            model.Cells[cur_x, cur_y - 1].Visited = true;
                            if (cur_x == end_x && cur_y - 1 == end_y)
                            {
                                UnmarkCells();
                                return true;
                            }
                        }
                    }

                    //проверка снизку от текущей позиции
                    if (cur_y < model.FieldSize - 1 && !(model.Cells[cur_x, cur_y + 1].TextureNumber > 0))
                    {
                        if (!(model.Cells[cur_x, cur_y + 1].Visited))
                        {
                            queue.Enqueue(model.Cells[cur_x, cur_y + 1]);
                            model.Cells[cur_x, cur_y + 1].Visited = true;
                            if (cur_x == end_x && cur_y + 1 == end_y)
                            {
                                UnmarkCells();
                                return true;
                            }
                        }
                    }
                }

                //в случае если не найден путь
                UnmarkCells();
                return false;
            }
            /// <summary>
            ///     Поиск линий длины 5 и более
            ///     Шары, оставляющие линию, помечаются
            /// </summary>
            /// <param name="с">Ячейка от которой начинается проверка</param>
            /// <param name="x">Координаты строки</param>
            /// <param name="y">Координаты столбца</param>
            private void FindLines(LinesCell c, int x, int y) 
            {
                int s1 = 0, s2 = 0, cur_x=x, cur_y=y;
                //влево-вправо
                while (cur_x > 0 && model.Cells[cur_x - 1, cur_y].TextureNumber > 0
                    && model.Cells[cur_x - 1, cur_y].TextureNumber == model.Cells[cur_x, cur_y].TextureNumber)
                {
                    s1++;
                    cur_x--;
                }
                cur_x = x;
                while (cur_x < model.FieldSize - 1 && model.Cells[cur_x + 1, cur_y].TextureNumber > 0
                    && model.Cells[cur_x + 1, cur_y].TextureNumber == model.Cells[cur_x, cur_y].TextureNumber)
                {
                    s2++;
                    cur_x++;
                }
                cur_x=x;
                if (s1 + s2 > 3)
                {
                    for (int i = cur_x - s1; i < cur_x + s2 + 1; i++)
                        model.Cells[i, cur_y].Visited = true;
                    //TODO здесь нужно передать цвет найденных линий в Coloring
                }
                s1 = 0;
                s2 = 0;
                //вверх-вниз
                while (cur_y > 0 && model.Cells[cur_x, cur_y - 1].TextureNumber > 0
                    && model.Cells[cur_x, cur_y - 1].TextureNumber == model.Cells[cur_x, cur_y].TextureNumber)
                {
                    s1++;
                    cur_y--;
                }
                cur_y = y;
                while (cur_y < model.FieldSize - 1 && model.Cells[cur_x, cur_y + 1].TextureNumber > 0
                    && model.Cells[cur_x, cur_y + 1].TextureNumber == model.Cells[cur_x, cur_y].TextureNumber)
                {
                    s2++;
                    cur_y++;
                }
                cur_y = y;
                if (s1 + s2 > 3)
                {
                    for (int i = cur_y - s1; i < cur_y + s2 + 1; i++)
                        model.Cells[cur_x, i].Visited = true;
                    //TODO здесь нужно передать цвет найденных линий в Coloring
                }
                s1 = 0;
                s2 = 0;
                //по диагонали
                while (cur_y > 0 && cur_x > 0 && model.Cells[cur_x - 1, cur_y - 1].TextureNumber > 0
                    && model.Cells[cur_x - 1, cur_y - 1].TextureNumber == model.Cells[cur_x, cur_y].TextureNumber)
                {
                    s1++;
                    cur_y--;
                    cur_x--;
                }
                cur_x = x;
                cur_y = y;
                while (cur_y < model.FieldSize - 1 && cur_x < model.FieldSize - 1
                    && model.Cells[cur_x + 1, cur_y + 1].TextureNumber > 0
                    && model.Cells[cur_x + 1, cur_y + 1].TextureNumber == model.Cells[cur_x, cur_y].TextureNumber)
                {
                    s2++;
                    cur_y++;
                    cur_x++;
                }
                cur_x = x;
                cur_y = y;
                if (s1 + s2 > 3)
                {
                    for (int i = cur_y - s1, j = cur_x - s1; i < cur_y + s2 + 1
                        && j < cur_x + s2 + 1; i++, j++)
                        model.Cells[j, i].Visited = true;
                    //TODO здесь нужно передать цвет найденных линий в Coloring
                }
                s1 = 0;
                s2 = 0;
                while (cur_y < model.FieldSize - 1 && x > 0 && model.Cells[cur_x - 1, cur_y + 1].TextureNumber > 0
                    && model.Cells[cur_x - 1, cur_y + 1].TextureNumber == model.Cells[cur_x, cur_y].TextureNumber)
                {
                    s1++;
                    cur_y++;
                    cur_x--;
                }
                cur_x = x;
                cur_y = y;
                while (cur_y > 0 && cur_x < model.FieldSize - 1 && model.Cells[cur_x + 1, cur_y - 1].TextureNumber > 0
                    && model.Cells[cur_x + 1, cur_y - 1].TextureNumber == model.Cells[cur_x, cur_y].TextureNumber)
                {
                    s2++;
                    cur_y--;
                    cur_x++;
                }
                cur_x = x;
                cur_y = y;
                if (s1 + s2 > 3)
                {
                    for (int i = cur_y + s1, j = cur_x - s1; i > cur_y - s2 - 1
                        && j < cur_x + s2 + 1; i--, j++)
                        model.Cells[j, i].Visited = true;
                    //TODO здесь нужно передать цвет найденных линий в Coloring
                }
            }

            /// <summary>
            ///     Проверка игры после очередного клика мыши
            /// </summary>
            /// <param name="с">Ячейка от которой начинается проверка</param>
            /// <param name="x">Координаты строки</param>
            /// <param name="y">Координаты столбца</param>
            /// @noop 0 - вставляем три новых шара
            /// @noop 1 - удалена линия и не нужно вставлять новых шаров
            /// @noop 2 - больше нет места для вставки шаров
            private int CheckGame(LinesCell c, int x, int y)
            {
                FindLines(c, x, y);
                if (DeleteLines() > 0) return 1;

                int Emptys = 0;
                for (int i = 0; i < model.FieldSize; i++)
                {
                    for (int j = 0; j < model.FieldSize; j++)
                    {
                        if (!(model.Cells[i, j].TextureNumber > 0))
                        {
                            Emptys++;
                        }
                    }
                }
                if (Emptys < 4)
                    return 2;
                return 0;
            }
            /// <summary>
            ///     Обработка клика мыши в пределах игрового поля.
            /// </summary>
            /// <param name="x">Координаты строки</param>
            /// <param name="y">Координаты столбца</param>
            private void CellClick(int x, int y)
            {
                    if (!(choosedCell.Choosed))
                    {
                        if (!(model.Cells[x,y].TextureNumber > 0)) return;
                        model.Cells[x, y].Choosed = true;
                        choosedCell = model.Cells[x, y];
                        choosedCellX = x;
                        choosedCellY = y;
                        return;
                    }
                    else if (model.Cells[x, y].TextureNumber > 0)
                    {
                        choosedCell.Choosed = false;
                        model.Cells[x, y].Choosed = true;
                        choosedCell = model.Cells[x, y];
                        choosedCellX = x;
                        choosedCellY = y;
                        return;
                    }
                    else if (!(Movable(choosedCellX, choosedCellY, x, y))) return;
                    else
                    {
                        choosedCell.Choosed = false;
                        model.Cells[x, y].TextureNumber = choosedCell.TextureNumber;
                        choosedCell.TextureNumber = 0;
                        int status;
                        if ((status = CheckGame(model.Cells[x, y], x, y)) != 2)
                        {
                            if (status == 0)
                                InsertOrbs();
                        }
                        else
                        {
                            //здесь конец игры
                        }
                        return;
                    }
            }
            /// <summary>
            ///     Проверка нажатия ЛКМ и выхода за границу
            ///     Если всё хорошо, преобразует координаты мыши в координаты игрового поля и передаёт в CellClick
            /// </summary>
            private void CellSearch()
            {
                MouseState currentMouseState = Mouse.GetState();
                if (currentMouseState.LeftButton != ButtonState.Pressed)
                    return;
                if (!(currentMouseState.X > model.FieldX && currentMouseState.Y > model.FieldY
                     && currentMouseState.X < model.FieldX + 9 * model.CellSize
                     && currentMouseState.Y < model.FieldY + 9 * model.CellSize))
                        return;

                CellClick((currentMouseState.X - model.FieldX)/model.CellSize, (currentMouseState.Y - model.FieldY)/model.CellSize);
            }

        }
    }
}