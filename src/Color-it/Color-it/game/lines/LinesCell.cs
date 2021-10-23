namespace Color_it.game.lines
{
    /// @brief Класс Ячейка для игры Lines
    /// @author eremchuk-mp-8
    class LinesCell
    {
        /// @brief Конструктор класса
        /// @details Создаём пустую ячейку
        public LinesCell()
        {
            Choosed = false;
            Visited = false;
            TextureNumber = EMPTY;
        }
        
        /// @brief Показывает, выбрана ли ячейка
        /// @details Во время игры выбрать можно только одну ячейку
        public bool Choosed { get; set; }
        
        /// @brief Показывает, посещена ли ячейка во время обхода
        /// @details Используется в алгоритме поиска пути и для удаления линий
        public bool Visited { get; set; }

        /// @brief Номер текстуры ячейки
        public int TextureNumber { get; set; }

    }

    /// @brief Список используемых текстур для ячеек
    /// @author tima2015
    enum TextureNumber : int
    {
        EMPTY = 0,
        RED = 1,
        BLUE = 2,
        GREEN = 3,
        YELLOW = 4,
        RED_SELECTED = 5,
        BLUE_SELECTED = 6,
        GREEN_SELECTED = 7,
        YELLOW_SELECTED = 8
    }
}
