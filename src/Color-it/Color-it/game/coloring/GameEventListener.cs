using System;
using Microsoft.Xna.Framework;

namespace Color_it.game.coloring
{
    /// <summary>
    ///     Реализация слушателя для основной игры.
    /// </summary>
    /// <see cref="IEventListener" />
    public class GameEventListener : IEventListener
    {
        public void Notify(IEvent @event)
        {
            //todo 
        }

        /// <summary>
        ///     Метод вызываемы при событии покраски.
        ///     Сообщает игре о необходимости закрасить сектора.
        /// </summary>
        private void OnCallToPainting(PaintingEvent @event)
        {
        }

        /// <summary>
        ///     Событие закрашивания фрагмента раскраски.
        /// </summary>
        public class PaintingEvent
        {
            public PaintingEvent(int sectorsToPaintingCount, Color color)
            {
                Color = color;
                SectorsToPaintingCount = sectorsToPaintingCount;
            }

            /// <summary>
            ///     Количество секторов которое должно быть закрашено.
            /// </summary>
            public int SectorsToPaintingCount { get; }

            /// <summary>
            ///     Цвет закрашивания.
            /// </summary>
            public Color Color { get; }
        }
    }
}