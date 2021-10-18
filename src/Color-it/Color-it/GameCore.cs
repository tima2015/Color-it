using System.IO;
using System.Resources;
using System.Text;
using System.Text.Json;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;

namespace Color_it
{
    /// <summary>
    /// Ядро программы
    /// </summary>
    /// @noop Используйте GameCore.Core.ResourceManager для получения необходимых ресурсов!
    /// @noop Позже приведу этот класс в нормальный вид
    public class GameCore //TODO дописать класс
    {
        public ResourceManager ResourceManager { get; } =
            ResourceManager.CreateFileBasedResourceManager("resources", "resources",null);

        public GameSettings Settings { get; } = GameSettings.Load();
        
        public Viewport SubGameViewport {get; } = new Viewport(0,0,1000,1000);

        public static GameCore Core { get; }
        
        /// <summary>
        /// Класс пользовательских настроек
        /// </summary>
        public class GameSettings
        {
            private static readonly string SettingsFilePath = "game_settings.json"; 
            
            /// <summary>
            /// Читает настройки из файла SettingsFilePath
            /// </summary>
            /// <returns>Сохранённые пользователем настройки или настройки по умолчанию</returns>
            public static GameSettings Load()
            {
                return JsonSerializer.Deserialize<GameSettings>(File.ReadAllText(SettingsFilePath, Encoding.UTF8));
            }

            /// <summary>
            /// Записывает настройки в файл SettingsFilePath
            /// </summary>
            /// <param name="settings">Настройки для записи</param>
            public static void save(GameSettings settings)
            {
                File.WriteAllText(SettingsFilePath, JsonSerializer.Serialize(settings), Encoding.UTF8);
            }

            /// <summary>
            /// Клавиша паузы и возвращения.
            /// По умолчанию Keys.Escape
            /// </summary>
            public Keys PauseAndBackKey { get; set; } = Keys.Escape;
            
            /// <summary>
            /// Клавиша подтверждения и перехода вперёд
            /// По умолчанию
            /// </summary>
            public Keys EnterAndForwardKey { get; set; } = Keys.Enter;

            /// <summary>
            /// Клавиша действия "Вверх"
            /// По умолчанию Keys.Up
            /// </summary>
            public Keys UpKey { get; set; } = Keys.Up;

            /// <summary>
            /// Клавиша действия "Вниз"
            /// По умолчанию Keys.Down
            /// </summary>
            public Keys DownKey { get; set; } = Keys.Down;

            /// <summary>
            /// Клавиша действия "Вправо"
            /// По умолчанию Keys.Right
            /// </summary>
            public Keys RightKey { get; set; } = Keys.Right;

            /// <summary>
            /// Клавиша действия "Влево"
            /// По умолчанию Keys.Left
            /// </summary>
            public Keys LeftKey { get; set; } = Keys.Left;

            /// <summary>
            /// Клавиша действия "Выбрать"
            /// По умолчанию Keys.Space
            /// </summary>
            public Keys SelectKey { get; set; } = Keys.Space;
        }
    }
}
