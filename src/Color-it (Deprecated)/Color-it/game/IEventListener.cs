namespace Color_it.game
{
    /// <summary>
    ///     Интерфейс для реализации паттерна "Слушатель" для каких-либо событий.
    /// </summary>
    public interface IEventListener
    {
        /// <summary>
        ///     Уведомляет слушателя о событии.
        /// </summary>
        /// <param name="event">Объект с данными о событии.</param>
        void Notify(IEvent @event);
    }

    /// <summary>
    ///     Интерфейс объекта, содержащий в себе данные о событии.
    /// </summary>
    public interface IEvent
    {
    }
}