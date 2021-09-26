using Microsoft.Xna.Framework.Graphics;

namespace Color_it.game
{
    /// <summary>
    ///     Класс содержащий в себе три объекта архитектурного паттерна "Модель Отоброжение Контроллер".
    /// </summary>
    public abstract class ModelViewController
    {
        /// <summary>
        ///     Конструктор требующий назначения модели, отображения и контроллера.
        /// </summary>
        protected ModelViewController(IModel model, IView view, IController controller)
        {
            Model = model;
            View = view;
            Controller = controller;
        }

        /// <summary>
        ///     Модель
        /// </summary>
        public IModel Model { get; }

        /// <summary>
        ///     Отображение
        /// </summary>
        public IView View { get; }

        /// <summary>
        ///     Контроллер
        /// </summary>
        public IController Controller { get; }
    }

    /// <summary>
    ///     Интерфейс модели.
    ///     Модель должна содержать в себе данные реализуемого объекта.
    ///     Модели не следует изменяться и визуализироваться самостоятельно.
    /// </summary>
    public interface IModel
    {
    }


    /// Интерфейс контроллера.
    /// Контроллер отвечает за обновление данных модели и взаимодействие с пользователем.
    /// Контроллер не должен напрямую влиять на отображение
    public interface IController
    {
        /// <summary>
        ///     Метод обновления.
        ///     В среднем вызывается 60 раз в секунду.
        /// </summary>
        /// <param name="delta">Время прошедшее с последнего обновления</param>
        void Update(float delta);
    }

    /// <summary>
    ///     Интерфейс отображения.
    ///     Отображение выводит данные модели на экран пользователя и не должно влиять на модель.
    /// </summary>
    public interface IView
    {
        /// <summary>
        ///     Метод отрисовки.
        ///     В среднем вызывается 60 раз в секунду.
        /// </summary>
        /// <param name="batch">Упаковщик спрайтов. Используется для отрисовки</param>
        void Draw(SpriteBatch batch);
    }

    /// <summary>
    ///     Класс метка для миниигр. Необходим для предотвращения попытки присвоения основной игры в качестве миниигры.
    /// </summary>
    public abstract class SubGame : ModelViewController
    {
        protected SubGame(IModel model, IView view, IController controller) : base(model, view, controller)
        {
        }
    }
}