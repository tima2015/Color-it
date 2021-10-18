using Microsoft.Xna.Framework.Graphics;

namespace Color_it.game.lines
{
    /// <summary>
    ///     Структура данных "ячейка".
    /// </summary>
    class LinesCell
    {
        /// @noop 0 - пустая ячейка
        /// @noop 1 - красный шар
        /// @noop 2 - синий шар
        /// @noop 3 - зелёный шар
        /// @noop 4 - жёлтый шар
        /// @noop 5 - красный выделенный шар
        /// @noop 6 - синий выделенный шар
        /// @noop 7 - зелёный выделенный шар
        /// @noop 8 - жёлтый выделенный шар
        private bool choosed, visited;
        private int textureNumber;
        public bool Choosed { get { return choosed; } set { choosed = value; } }
        public bool Visited { get { return visited; } set { visited = value; } }
        public int TextureNumber { get { return textureNumber; } set { textureNumber = value; } }

        public LinesCell()
        {
            choosed = false;
            visited = false;
            textureNumber = 0;
        }
    }
}