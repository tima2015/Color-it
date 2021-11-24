using System;
using Color_it.game.coloring;
using Microsoft.Xna.Framework.Graphics;
using TexturePackerLoader;

namespace Color_it.game.match_three
{
    /// <summary>
    ///     Класс реализующий мини игру "Три в ряд"
    /// </summary>
    public class MatchThreeSubGame : SubGame
    {
        public MatchThreeSubGame(GameEventListener listener) :
            base(new MatchThreeModel(), new MatchThreeView(), new MatchThreeController(listener))
        {
        }

        /// <summary>
        ///     Реализация модели мини игры.
        /// </summary>
        private class MatchThreeModel : IModel
        {
        }

        /// <summary>
        ///     Реализация отображения мини игры.
        /// </summary>
        private class MatchThreeView : IView
        {
            public void Draw(SpriteBatch batch, SpriteRender render)
            {
                throw new NotImplementedException();
            }
        }

        /// <summary>
        ///     Реализация контроллера мини игры.
        /// </summary>
        private class MatchThreeController : IController
        {
            //при выполнии условия закрашивания вызывайте listener.Notify(new PaintingEvent(count, color))
            //для подробностей смотрите GameEventListenr.cs
            private GameEventListener _listener;

            public MatchThreeController(GameEventListener listener)
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