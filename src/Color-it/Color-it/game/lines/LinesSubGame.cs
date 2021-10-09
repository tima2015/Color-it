using System;
using Color_it.game.coloring;
using Microsoft.Xna.Framework.Graphics;

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
            private const int FieldSize = 9;
            private LinesCell[,] cells;
            private LinesCell[] NextCells;
            private LinesCell ChoosedCell;
            Texture2D[] textures;
            private int[] orbs = new int[3];

            public LinesModel()
            {
                cells = new LinesCell[FieldSize, FieldSize];
                NextCells = new LinesCell[3];
            }
            //Удаление закрашенных линии
            //Возвращает кол-во освободившихся клеток
            public int DeleteLines()
            {
                int del = 0;
                for (int i = 0; i < FieldSize; i++)
                {
                    for (int j = 0; j < FieldSize; j++)
                    {
                        if (cells[i, j].Visited)
                        {
                            cells[i, j].Visited = false;
                            cells[i, j].IsOrb = false;
                            del++;
                        }
                    }
                }
                return del;
            }
            //Выбор 3-х случайных клеток для вставки шаров
            public void InsertOrbs()
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
                        if (!(cells[orbs[0] / FieldSize, orbs[0] % FieldSize].IsOrb
                            || cells[orbs[1] / FieldSize, orbs[1] % FieldSize].IsOrb
                            || cells[orbs[2] / FieldSize, orbs[2] % FieldSize].IsOrb))
                            break;
                }
                for (int i = 0; i < FieldSize; i++)
                {
                    for (int j = 0; j < FieldSize; j++)
                    {
                        for (int k = 0; k < 3; k++)
                        {
                            if (i == orbs[k] / FieldSize && j == orbs[k] % FieldSize)
                            {
                                cells[i, j].IsOrb = true;
                                cells[i, j].Texture = NextCells[k].Texture;
                                break;
                            }
                        }
                    }
                }
                //Выбор 3-х следующих шаров для вставки
                for (int i = 0; i < 3; i++)
                {
                    NextCells[i].Texture = textures[rand.Next(0, 4)];
                }
            }
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

            public LinesController(GameEventListener listener)
            {
                _listener = listener;
            }

            public void Update(float delta)
            {
                throw new NotImplementedException();
            }

            public void OnBegin()
            {
                throw new NotImplementedException();
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
        }
    }
}