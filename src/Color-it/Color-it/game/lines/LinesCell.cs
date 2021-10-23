namespace Color_it.game.lines
{
    /// @brief
    /// @author eremchuk-mp-8
    /// @details
    class LinesCell
    {
        
        /// @brief
        /// @details
        public LinesCell()
        {
            Choosed = false;
            Visited = false;
            TextureNumber = 0;
        }
        
        /// @brief
        /// @details
        public bool Choosed { get; set; }
        
        /// @brief
        /// @details
        public bool Visited { get; set; }

        /// @brief
        /// @details
        public int TextureNumber { get; set; }

    }

    /// @brief
    /// @author tima2015
    /// @details
    /// @noop я пока не знаю то сюда вписать, единственное скажу, что магические числа лучше не использовать
    /// @noop с константами же лучше всё понятно
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