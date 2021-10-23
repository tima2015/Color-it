using System;
using System.Collections.Generic;
using Color_it.game.coloring;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using static Color_it.game.coloring.GameEventListener;

namespace Color_it.game.lines
{
    
    /// @brief Класс реализующий мини игру "Линии"
    public class LinesSubGame : SubGame
    {
        public LinesSubGame(GameEventListener listener) :
            base(new LinesModel(), new LinesView(), new LinesController(listener))
        {
        }

        /// @brief Модель мини игры "Линии"
        /// @author eremchuk-mp-8
        /// @details
        private class LinesModel : IModel
        {
            /// @brief
            /// @details
            public const int FieldSize = 9;
            
            /// @brief
            /// @details
            public const int CellSize = 40;

            /// @brief Количество ячеек
            /// @details
            public const int CellsCount = FieldSize * FieldSize;

            /// @brief Количество шаров при запуске игры
            /// @details
            public const int BallsOnInitialize = 3;

            /// @brief
            /// @details
            public LinesModel()
            {
                Cells = new LinesCell[FieldSize, FieldSize];
                NextCells = new LinesCell[3];
                for (var i = 0; i < NextCells.Length; i++) NextCells[i] = new LinesCell();
                FieldX = GameCore.Core.SubGameViewport.Width / 2 - 5 * CellSize;
                FieldX = GameCore.Core.SubGameViewport.Height / 2 - 5 * CellSize;
            }
            
            /// @brief
            /// @details
            public LinesCell[,] Cells { get; }

            /// @brief
            /// @details
            public LinesCell[] NextCells { get; }
            
            /// @brief
            /// @details
            public int FieldX { get; }
            
            /// @brief
            /// @details
            public int FieldY { get; }
        }

            /// @brief Реализация отображения мини игры.
            /// @author eremchuk-mp-8
            /// @details
            private class LinesView : IView
        {
            public void Draw(SpriteBatch batch)
            {
                throw new NotImplementedException();
            }
        }
            
        /// @brief Реализация контроллера мини игры.
        /// @author eremchuk-mp-8
        /// @details
        private class LinesController : IController
        {
            //при выполнии условия закрашивания вызывайте listener.Notify(new PaintingEvent(count, color))
            //для подробностей смотрите GameEventListenr.cs
            private GameEventListener _listener;//warning!
            private LinesCell _choosedCell;
            private int _choosedCellX, _choosedCellY;
            private readonly int[] _orbs = new int[3];
            private readonly LinesModel _model = new();

            public LinesController(GameEventListener listener)
            {
                _listener = listener;
            }

            public void Update(float delta)
            {
                CellSearch();                
            }

            /// @brief Назначение NextCells случайных значений
            /// @details 
            private void InitWithRandNextCells(Random rand)
            {
                for (var i = 0; i < LinesModel.BallsOnInitialize; i++)
                    _model.NextCells[i].TextureNumber = rand.Next((int) TextureNumber.RED, (int) TextureNumber.YELLOW);
            }

            /// @brief
            /// @details
            /// @noop Это же поиск? На мой взгляд, довольно неоптимизированый, в худшем случае пользователь может ждать очень долго
            private void FindOrbs(Random rand)
            {
                while (true)
                {
                    for (int i = 0; i < 3; i++)
                    {
                        _orbs[i] = rand.Next(0, LinesModel.CellsCount);
                    }
                    if (!(_orbs[0] == _orbs[1] || _orbs[0] == _orbs[2]
                                               || _orbs[1] == _orbs[2]))
                        break;
                }
            }

            /// @brief
            /// @details
            private void InitCellsGrid()
            {
                for (int i = 0; i < LinesModel.FieldSize; i++)
                for (int j = 0; j < LinesModel.FieldSize; j++)
                {
                    _model.Cells[i, j] = new LinesCell();
                    for (int k = 0; k < LinesModel.BallsOnInitialize; k++)
                    {
                        if (_orbs[k] / LinesModel.FieldSize != i || _orbs[k] % LinesModel.FieldSize != j) continue;
                        _model.Cells[i, j].TextureNumber = _model.NextCells[k].TextureNumber;
                        break;
                    }
                }
            }

            /// @brief Вызывается единожды перед запуском игры
            /// @details
            public void OnBegin()
            {
                _choosedCell = new LinesCell();
                var rand = new Random();
                InitWithRandNextCells(rand);
                FindOrbs(rand);
                InitCellsGrid();
                InitWithRandNextCells(rand);
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
                for (int i = 0; i < LinesModel.FieldSize; i++)
                {
                    for (int j = 0; j < LinesModel.FieldSize; j++)
                    {
                        if (_model.Cells[i, j].Visited)
                        {
                            _model.Cells[i, j].Visited = false;
                            _model.Cells[i, j].TextureNumber = 0;
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
                        _orbs[i] = rand.Next(0, 81);
                    }
                    if (!(_orbs[0] == _orbs[1] || _orbs[0] == _orbs[2] || _orbs[1] == _orbs[2]))
                        if (!(_model.Cells[_orbs[0] / LinesModel.FieldSize, _orbs[0] % LinesModel.FieldSize].TextureNumber > 0
                            || _model.Cells[_orbs[1] / LinesModel.FieldSize, _orbs[1] % LinesModel.FieldSize].TextureNumber > 0
                            || _model.Cells[_orbs[2] / LinesModel.FieldSize, _orbs[2] % LinesModel.FieldSize].TextureNumber > 0))
                            break;
                }
                for (int i = 0; i < LinesModel.FieldSize; i++)
                {
                    for (int j = 0; j < LinesModel.FieldSize; j++)
                    {
                        for (int k = 0; k < 3; k++)
                        {
                            if (i == _orbs[k] / LinesModel.FieldSize && j == _orbs[k] % LinesModel.FieldSize)
                            {
                                _model.Cells[i, j].TextureNumber = _model.NextCells[k].TextureNumber;
                                break;
                            }
                        }
                    }
                }
                //Выбор 3-х следующих шаров для вставки
                for (int i = 0; i < 3; i++)
                {
                    _model.NextCells[i].TextureNumber = rand.Next(1, 5);
                }
            }
            /// <summary>
            ///     Снятие всех меток Visited
            /// </summary>
            private void UnmarkCells()
            {
                foreach (LinesCell c in _model.Cells)
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
                queue.Enqueue(_model.Cells[start_x, start_y]);
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
                    if (cur_x > 0 && !(_model.Cells[cur_x - 1, cur_y].TextureNumber > 0))
                    {
                        if (!(_model.Cells[cur_x - 1, cur_y].Visited))
                        {
                            queue.Enqueue(_model.Cells[cur_x - 1, cur_y]);
                            _model.Cells[cur_x - 1, cur_y].Visited = true;
                            if (cur_x  == end_x && cur_y == end_y)
                            {
                                UnmarkCells();
                                return true;
                            }
                        }
                    }

                    //проверка справа от текущей позиции
                    if (cur_x < LinesModel.FieldSize - 1 && !(_model.Cells[cur_x + 1, cur_y].TextureNumber > 0))
                    {
                        if (!(_model.Cells[cur_x + 1, cur_y].Visited))
                        {
                            queue.Enqueue(_model.Cells[cur_x + 1, cur_y]);
                            _model.Cells[cur_x + 1, cur_y].Visited = true;
                            if (cur_x + 1 == end_x && cur_y == end_y)
                            {
                                UnmarkCells();
                                return true;
                            }
                        }
                    }

                    //проверка сверху от текущей позиции
                    if (cur_y > 0 && !(_model.Cells[cur_x, cur_y - 1].TextureNumber > 0))
                    {
                        if (!(_model.Cells[cur_x, cur_y - 1].Visited))
                        {
                            queue.Enqueue(_model.Cells[cur_x, cur_y - 1]);
                            _model.Cells[cur_x, cur_y - 1].Visited = true;
                            if (cur_x == end_x && cur_y - 1 == end_y)
                            {
                                UnmarkCells();
                                return true;
                            }
                        }
                    }

                    //проверка снизку от текущей позиции
                    if (cur_y < LinesModel.FieldSize - 1 && !(_model.Cells[cur_x, cur_y + 1].TextureNumber > 0))
                    {
                        if (!(_model.Cells[cur_x, cur_y + 1].Visited))
                        {
                            queue.Enqueue(_model.Cells[cur_x, cur_y + 1]);
                            _model.Cells[cur_x, cur_y + 1].Visited = true;
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
                while (cur_x > 0 && _model.Cells[cur_x - 1, cur_y].TextureNumber > 0
                    && _model.Cells[cur_x - 1, cur_y].TextureNumber == _model.Cells[cur_x, cur_y].TextureNumber)
                {
                    s1++;
                    cur_x--;
                }
                cur_x = x;
                while (cur_x < LinesModel.FieldSize - 1 && _model.Cells[cur_x + 1, cur_y].TextureNumber > 0
                                                        && _model.Cells[cur_x + 1, cur_y].TextureNumber == _model.Cells[cur_x, cur_y].TextureNumber)
                {
                    s2++;
                    cur_x++;
                }
                cur_x=x;
                if (s1 + s2 > 3)
                {
                    for (int i = cur_x - s1; i < cur_x + s2 + 1; i++)
                        _model.Cells[i, cur_y].Visited = true;
                    //TODO здесь нужно передать цвет найденных линий в Coloring
                }
                s1 = 0;
                s2 = 0;
                //вверх-вниз
                while (cur_y > 0 && _model.Cells[cur_x, cur_y - 1].TextureNumber > 0
                    && _model.Cells[cur_x, cur_y - 1].TextureNumber == _model.Cells[cur_x, cur_y].TextureNumber)
                {
                    s1++;
                    cur_y--;
                }
                cur_y = y;
                while (cur_y < LinesModel.FieldSize - 1 && _model.Cells[cur_x, cur_y + 1].TextureNumber > 0
                                                        && _model.Cells[cur_x, cur_y + 1].TextureNumber == _model.Cells[cur_x, cur_y].TextureNumber)
                {
                    s2++;
                    cur_y++;
                }
                cur_y = y;
                if (s1 + s2 > 3)
                {
                    for (int i = cur_y - s1; i < cur_y + s2 + 1; i++)
                        _model.Cells[cur_x, i].Visited = true;
                    //TODO здесь нужно передать цвет найденных линий в Coloring
                }
                s1 = 0;
                s2 = 0;
                //по диагонали
                while (cur_y > 0 && cur_x > 0 && _model.Cells[cur_x - 1, cur_y - 1].TextureNumber > 0
                    && _model.Cells[cur_x - 1, cur_y - 1].TextureNumber == _model.Cells[cur_x, cur_y].TextureNumber)
                {
                    s1++;
                    cur_y--;
                    cur_x--;
                }
                cur_x = x;
                cur_y = y;
                while (cur_y < LinesModel.FieldSize - 1
                       && cur_x < LinesModel.FieldSize - 1 
                       && _model.Cells[cur_x + 1, cur_y + 1].TextureNumber > 0
                       && _model.Cells[cur_x + 1, cur_y + 1].TextureNumber == _model.Cells[cur_x, cur_y].TextureNumber)
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
                        _model.Cells[j, i].Visited = true;
                    //TODO здесь нужно передать цвет найденных линий в Coloring
                }
                s1 = 0;
                s2 = 0;
                while (cur_y < LinesModel.FieldSize - 1 && x > 0 && _model.Cells[cur_x - 1, cur_y + 1].TextureNumber > 0
                    && _model.Cells[cur_x - 1, cur_y + 1].TextureNumber == _model.Cells[cur_x, cur_y].TextureNumber)
                {
                    s1++;
                    cur_y++;
                    cur_x--;
                }
                cur_x = x;
                cur_y = y;
                while (cur_y > 0 && cur_x < LinesModel.FieldSize - 1 && _model.Cells[cur_x + 1, cur_y - 1].TextureNumber > 0
                    && _model.Cells[cur_x + 1, cur_y - 1].TextureNumber == _model.Cells[cur_x, cur_y].TextureNumber)
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
                        _model.Cells[j, i].Visited = true;
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
                for (int i = 0; i < LinesModel.FieldSize; i++)
                {
                    for (int j = 0; j < LinesModel.FieldSize; j++)
                    {
                        if (!(_model.Cells[i, j].TextureNumber > 0))
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
                    if (!(_choosedCell.Choosed))
                    {
                        if (!(_model.Cells[x,y].TextureNumber > 0)) return;
                        _model.Cells[x, y].Choosed = true;
                        _choosedCell = _model.Cells[x, y];
                        _choosedCellX = x;
                        _choosedCellY = y;
                        return;
                    }
                    else if (_model.Cells[x, y].TextureNumber > 0)
                    {
                        _choosedCell.Choosed = false;
                        _model.Cells[x, y].Choosed = true;
                        _choosedCell = _model.Cells[x, y];
                        _choosedCellX = x;
                        _choosedCellY = y;
                        return;
                    }
                    else if (!(Movable(_choosedCellX, _choosedCellY, x, y))) return;
                    else
                    {
                        _choosedCell.Choosed = false;
                        _model.Cells[x, y].TextureNumber = _choosedCell.TextureNumber;
                        _choosedCell.TextureNumber = 0;
                        int status;
                        if ((status = CheckGame(_model.Cells[x, y], x, y)) != 2)
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
                if (!(currentMouseState.X > _model.FieldX && currentMouseState.Y > _model.FieldY
                     && currentMouseState.X < _model.FieldX + 9 * LinesModel.CellSize
                     && currentMouseState.Y < _model.FieldY + 9 * LinesModel.CellSize))
                        return;

                CellClick((currentMouseState.X - _model.FieldX)/LinesModel.CellSize, (currentMouseState.Y - _model.FieldY)/LinesModel.CellSize);
            }

        }
    }
}