using System;
using System.Collections.Generic;
using Color_it.game.coloring;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using TexturePackerLoader;
using static Color_it.game.coloring.GameEventListener;

namespace Color_it.game.lines
{
    
    /// @brief Класс реализующий мини игру "Линии"
    public class LinesSubGame : SubGame
    {
        public LinesSubGame(GameEventListener listener) :
            base(new LinesModel(), new LinesView(), new LinesController(listener))
        {
            ((LinesView) View).Model = (LinesModel) Model;
            ((LinesController) Controller).Model = Model;
        }

        /// @brief Модель мини игры "Линии"
        /// @author eremchuk-mp-8
        /// @details
        private class LinesModel : IModel
        {
            /// @brief Длина стороны игрового поля в ячейках
            /// @details
            public const int FieldSize = 9;
            
            /// @brief Длина стороны одной ячейки
            /// @details
            public const int CellSize = 40;

            /// @brief Количество ячеек
            /// @details
            public const int CellsCount = FieldSize * FieldSize;

            /// @brief Количество шаров для вставки
            /// @details
            public const int BallsInsertCount = 3;

            /// @brief Объявление модели мини-игры Lines
            /// @details Объявляются массив ячеек игрового поля и массив ячеек на следующем ходе,
            /// устанавливается положение игрового поля
            public LinesModel()
            {
                Cells = new LinesCell[FieldSize, FieldSize];
                NextCells = new LinesCell[3];
                for (var i = 0; i < NextCells.Length; i++) NextCells[i] = new LinesCell();
                FieldX = GameCore.Core.SubGameViewport.Width / 2 - (FieldSize/2 + 1) * CellSize;
                FieldX = GameCore.Core.SubGameViewport.Height / 2 - (FieldSize/2 + 1) * CellSize;
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
            Texture2D _texture;
            private LinesModel _model;
            public LinesView()
            {
            }

            public LinesModel Model
            {
                get => _model;
                set => _model = value;
            }

            public void Draw(SpriteBatch batch, SpriteRender render)
            {
                batch.Begin();
                for(int i=0; i < LinesModel.FieldSize; i++)
                {
                    for(int j=0; j < LinesModel.FieldSize; j++)
                    {
                        batch.Draw(_texture,
                                    new Vector2(GameCore.Core.SubGameViewport.X + _model.FieldX + i * LinesModel.CellSize,
                                                GameCore.Core.SubGameViewport.Y + _model.FieldY + j * LinesModel.CellSize),
                                    new Rectangle(320 * _model.Cells[i, j].TextureNumber, 0, 320 * (_model.Cells[i, j].TextureNumber + 1), 320),
                                    Color.Black,
                                    0,
                                    Vector2.Zero,
                                    (float)320 / LinesModel.CellSize,
                                    SpriteEffects.FlipVertically,
                                    0);

                    }
                }
                batch.End();
            }
        }
            
        /// @brief Реализация контроллера мини игры.
        /// @author eremchuk-mp-8
        /// @details
        public class LinesController : IController
        {
            
            //#####################################
            //######## !!!FOR TEST ONLY!!! ########
            //#####################################

            public static int TEST_XCellIndexByOrb(LinesController controller, int orb)
            {
                return controller.XCellIndexByOrb(orb);
            }

            public static int TEST_YCellIndexByOrb(LinesController controller, int orb)
            {
                return controller.YCellIndexByOrb(orb);
            }

            public static bool TEST_IsOrbsNotEquals(LinesController controller)
            {
                return controller.IsOrbsNotEquals();
            }

            public static int TEST_DeleteLines(LinesController controller)
            {
                return controller.DeleteLines();
            }

            public static LinesCell TEST_CellByOrb(LinesController controller, int orb)
            {
                return controller.CellByOrb(orb);
            }

            public static bool IsOrbsCellsNotEmpty(LinesController controller)
            {
                return controller.IsOrbsCellsNotEmpty();
            }

            //#####################################
            //######## FOR TEST ONLY ENDS. ########
            //#####################################
            
            //при выполнии условия закрашивания вызывайте listener.Notify(new PaintingEvent(count, color))
            //для подробностей смотрите GameEventListenr.cs
            private GameEventListener _listener;//warning!
            private LinesCell _choosedCell;
            private int _choosedCellX, _choosedCellY;
            private readonly int[] _orbs = new int[LinesModel.BallsInsertCount];
            private LinesModel _model;
            internal enum Direction
            {
                LEFT_UP = 10, LEFT_DOWN = 9, LEFT = 8, RIGHT_UP = 6, RIGHT_DOWN = 5, RIGHT = 4, UP = 2, DOWN = 1
            }

            public LinesController(GameEventListener listener)
            {
                _listener = listener;
            }

            public IModel Model
            {
                get => _model;
                set => _model = (LinesModel) value;
            }

            public void Update(float delta)
            {
                CellSearch();                
            }

            /// @brief Назначение NextCells случайных значений
            /// @details 
            private void InitWithRandNextCells(Random rand)
            {
                for (var i = 0; i < LinesModel.BallsInsertCount; i++)
                    _model.NextCells[i].TextureNumber = rand.Next((int) TextureNumber.RED, (int) TextureNumber.YELLOW);
            }
            
            /// @brief Проверка несовпадения сфер
            /// @details
            /// @return истина если все три сферы уникальны.
            private bool IsOrbsNotEquals()
            {
                return !(_orbs[0] == _orbs[1] || _orbs[0] == _orbs[2]
                                              || _orbs[1] == _orbs[2]);
            }

            /// @brief
            /// @details
            private void FindOrbs(Random rand)
            {
                while (true)
                {
                    for (int i = 0; i < LinesModel.BallsInsertCount; i++)
                    {
                        _orbs[i] = rand.Next(0, LinesModel.CellsCount);
                    }
                    if (IsOrbsNotEquals())
                        break;
                }
            }

            /// @brief Получение индекса по X для ячейки по сфере
            /// @param[in] orb номер сферы
            /// @details
            /// @return индекс по X ячейки.
            private int XCellIndexByOrb(int orb)
            {
                return orb % LinesModel.FieldSize;
            }

            /// @brief Получение индекса по Y для ячейки по сфере
            /// @param[in] orb номер сферы
            /// @details
            /// @return индекс по Y ячейки.
            private int YCellIndexByOrb(int orb)
            {
                return orb / LinesModel.FieldSize;
            }

            /// @brief
            /// @details
            private void InitCellsGrid()
            {
                for (int i = 0; i < LinesModel.FieldSize; i++)
                for (int j = 0; j < LinesModel.FieldSize; j++)
                {
                    _model.Cells[i, j] = new LinesCell();
                    for (int k = 0; k < LinesModel.BallsInsertCount; k++)
                    {
                        if (XCellIndexByOrb(_orbs[k]) != i || YCellIndexByOrb(_orbs[i]) != j) continue;
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
            { }

            public void OnPause()
            { }

            public void OnEnd()
            { }
            
            /// @brief Удаление закрашенных линии
            /// @return Возвращает кол-во освободившихся клеток
            /// @details
            private int DeleteLines()
            {
                int del = 0;
                for (int i = 0; i < LinesModel.FieldSize; i++)
                {
                    for (int j = 0; j < LinesModel.FieldSize; j++)
                    {
                        if (!_model.Cells[i, j].Visited) continue;
                        _model.Cells[i, j].Visited = false;
                        _model.Cells[i, j].TextureNumber = 0;
                        del++;
                    }
                }
                return del;
            }

            /// @brief Получение ячейки по номеру сферы
            /// @return Возвращает ячейку
            /// @details
            private LinesCell CellByOrb(int orb)
            {
                return _model.Cells[XCellIndexByOrb(orb), YCellIndexByOrb(orb)];
            }

            /// @brief Проверка ячеек на заполненость
            /// @return Возвращает истину если ячейки сфер не пусты
            /// @details
            private bool IsOrbsCellsNotEmpty()
            {
                return IsOrbsNotEquals() && !(CellByOrb(_orbs[0]).TextureNumber > 0 || CellByOrb(_orbs[1]).TextureNumber > 0 
                    || CellByOrb(_orbs[2]).TextureNumber > 0);
            }

            /// @brief Выбор BallsInsertCount случайных клеток для вставки шаров
            /// @details
            private void InsertOrbs()
            {
                var rand = new Random();
                //Ищем 3 пустые клетки
                while (true)
                {
                    for (int i = 0; i < LinesModel.BallsInsertCount; i++) _orbs[i] = rand.Next(0, LinesModel.CellsCount);
                    if (IsOrbsCellsNotEmpty()) break;
                }
                for (int i = 0; i < LinesModel.FieldSize; i++)
                for (int j = 0; j < LinesModel.FieldSize; j++)
                for (int k = 0; k < LinesModel.BallsInsertCount; k++)
                    if (i == XCellIndexByOrb(_orbs[k]) && j == YCellIndexByOrb(_orbs[k]))
                    {
                        _model.Cells[i, j].TextureNumber = _model.NextCells[k].TextureNumber;
                        break;
                    }
                //Выбор BallsInsertCount следующих шаров для вставки
                InitWithRandNextCells(rand);
            }
            
            /// @brief Снятие всех меток Visited
            private void UnmarkCells()
            {
                foreach (var c in _model.Cells) c.Visited = false;
            }

            private int[] IntToBinary(int x)
            {
                int[] a = new int[4];
                for(int i = 3; i > -1; i--)
                {
                    a[i] = x % 2;
                    if (x == 1)
                        break;
                    x /= 2;
                }
                return a;
            }

            /// @brief Проверка в заданном направлении
            /// @details Для определения направления переводим параметр direction в двоичное представление
            /// @param[in] direction Направление (принимает значения LEFT, RIGHT, UP или DOWN)
            /// @param[in] cur_x Координаты строки текущей ячейки
            /// @param[in] cur_y Координаты столбца текущей ячейки
            /// @param[in] end_x Координаты строки конечной ячейки
            /// @param[in] end_y Координаты столбца конечной ячейки
            /// @param[in] queue Очередь из просматриваемых ячеек
            private bool CheckingDirection(int direction, int cur_y,  int cur_x, int end_x, int end_y, ref Queue<LinesCell> queue)
            {
                int[] a = IntToBinary(direction);
                int left = a[0], right = a[1], up = a[2], down = a[3];
                if (cur_x - left + right >= 0 && cur_y - up + down >= 0 && cur_y - up + down <= LinesModel.FieldSize && cur_x - left + right <= LinesModel.FieldSize
                    && !(_model.Cells[cur_x - left + right, cur_y - up + down].TextureNumber > 0))
                {
                    if (!(_model.Cells[cur_x - left + right, cur_y - up + down].Visited))
                    {
                        cur_x += right - left;
                        cur_y += down - up;
                        queue.Enqueue(_model.Cells[cur_x, cur_y]);
                        _model.Cells[cur_x, cur_y].Visited = true;
                        
                        if (cur_x == end_x && cur_y == end_y)
                        {
                            UnmarkCells();
                            return true;
                        }
                    }
                }
                return false;
            }

            /// @brief Поиск пути от start до end
            /// @details Реализовано с помощью алгоритма BFS
            /// @param[in] start_x Координаты строки стартовой ячейки
            /// @param[in] start_y Координаты столбца стартовой ячейки
            /// @param[in] end_x Координаты строки конечной ячейки
            /// @param[in] end_y Координаты столбца конечной ячейки
            private bool Movable(int start_x, int start_y, int end_x, int end_y)
            {
                Queue<LinesCell> queue = new();
                queue.Enqueue(_model.Cells[start_x, start_y]);
                int cur_x = start_x, cur_y = start_y;

                while (queue.Count > 0)
                {
                    LinesCell cur = queue.Dequeue();
                    for(int i=0; i < LinesModel.FieldSize; i++)
                    {
                        for(int j=0; j < LinesModel.FieldSize; j++)
                        {
                            if(_model.Cells[i,j].Equals(cur))
                            {
                                cur_x = i;
                                cur_y = j;
                            }
                        }
                    }
              
                    if (cur_x == end_x && cur_y == end_y)
                    {
                        UnmarkCells();
                        return true;
                    }
                    cur.Visited = true;
                    if (CheckingDirection((int)Direction.LEFT, cur_x, cur_y, end_x, end_y, ref queue) ||
                        CheckingDirection((int)Direction.RIGHT, cur_x, cur_y, end_x, end_y, ref queue) ||
                        CheckingDirection((int)Direction.UP, cur_x, cur_y, end_x, end_y, ref queue) ||
                        CheckingDirection((int)Direction.DOWN, cur_x, cur_y, end_x, end_y, ref queue))
                        return true;
                }

                //в случае если не найден путь
                UnmarkCells();
                return false;
            }

            private Color DefineColor(int cur_x, int cur_y)
            {
                Color color = Color.White;
                switch (_model.Cells[cur_x, cur_y].TextureNumber)
                {
                    case (int)lines.TextureNumber.RED:
                        color = Color.Red;
                        break;
                    case (int)lines.TextureNumber.BLUE:
                        color = Color.Blue;
                        break;
                    case (int)lines.TextureNumber.GREEN:
                        color = Color.Green;
                        break;
                    case (int)lines.TextureNumber.YELLOW:
                        color = Color.Yellow;
                        break;
                }
                return color;
            }
            
            /// <summary>
            ///     Поиск линий длины 5 и более
            ///     Шары, оставляющие линию, помечаются
            /// </summary>
            /// <param name="x">Координаты строки</param>
            /// <param name="y">Координаты столбца</param>
            private void FindLines(int x, int y) //todo Нужно разбить этот метод на группу более мелких методов
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
                if (s1 + s2 > LinesModel.BallsInsertCount)
                {
                    for (int i = cur_x - s1; i < cur_x + s2 + 1; i++)
                        _model.Cells[i, cur_y].Visited = true;
                    _listener.Notify((IEvent)new PaintingEvent(s1 + s2 + 1, DefineColor(cur_x, cur_y)));

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
                if (s1 + s2 > LinesModel.BallsInsertCount)
                {
                    for (int i = cur_y - s1; i < cur_y + s2 + 1; i++)
                        _model.Cells[cur_x, i].Visited = true;
                    _listener.Notify((IEvent)new PaintingEvent(s1 + s2 + 1, DefineColor(cur_x, cur_y)));
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
                if (s1 + s2 > LinesModel.BallsInsertCount)
                {
                    for (int i = cur_y - s1, j = cur_x - s1; i < cur_y + s2 + 1
                        && j < cur_x + s2 + 1; i++, j++)
                        _model.Cells[j, i].Visited = true;
                    _listener.Notify((IEvent)new PaintingEvent(s1 + s2 + 1, DefineColor(cur_x, cur_y)));
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
                if (s1 + s2 > LinesModel.BallsInsertCount)
                {
                    for (int i = cur_y + s1, j = cur_x - s1; i > cur_y - s2 - 1
                        && j < cur_x + s2 + 1; i--, j++)
                        _model.Cells[j, i].Visited = true;
                    _listener.Notify((IEvent)new PaintingEvent(s1 + s2 + 1, DefineColor(cur_x, cur_y)));
                }
            }
            
            /// @brief вставляем три новых шара
            public const int DoInsert = 0;
            
            /// @brief удалена линия и не нужно вставлять новых шаров
            public const int LineDeleted = 1;
            
            /// @brief больше нет места для вставки шаров
            public const int GridFull = 2;

            /// <summary>
            ///     Проверка игры после очередного клика мыши
            /// </summary>
            /// <param name="x">Координаты строки</param>
            /// <param name="y">Координаты столбца</param>
            private int CheckGame(int x, int y)
            {
                FindLines(x, y);
                if (DeleteLines() > 0) return LineDeleted;

                int emptys = 0;
                for (int i = 0; i < LinesModel.FieldSize; i++)
                for (int j = 0; j < LinesModel.FieldSize; j++)
                    if (!(_model.Cells[i, j].TextureNumber > 0))
                        emptys++;

                if (emptys < 4)
                    return GridFull;
                return DoInsert;
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
                        if ((status = CheckGame(x, y)) != GridFull)
                        {
                            if (status == DoInsert)
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
                     && currentMouseState.X < _model.FieldX + LinesModel.FieldSize * LinesModel.CellSize
                     && currentMouseState.Y < _model.FieldY + LinesModel.FieldSize * LinesModel.CellSize))
                        return;

                CellClick((currentMouseState.X - _model.FieldX)/LinesModel.CellSize, (currentMouseState.Y - _model.FieldY)/LinesModel.CellSize);
            }

        }
    }
}