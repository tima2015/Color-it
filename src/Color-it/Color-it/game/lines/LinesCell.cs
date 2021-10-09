using Microsoft.Xna.Framework.Graphics;

namespace Color_it.game.lines
{
    /// <summary>
    ///     Структура данных "ячейка".
    /// </summary>
    class LinesCell
    {
        private bool is_orb, choosed, visited;
        private Texture2D texture;
        public Texture2D Texture { get { return texture; } set { texture = value; } }
        public bool Choosed { get { return choosed; } set { choosed = value; } }
        public bool IsOrb { get { return is_orb; } set { is_orb = value; } }
        public bool Visited { get { return visited; } set { visited = value; } }

        public LinesCell()
        {
            is_orb = false;
            choosed = false;
            visited = false;
        }
    }
}