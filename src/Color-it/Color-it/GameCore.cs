using System;
using System.IO;
using System.Linq;
using System.Resources;
using System.Text;
using System.Text.Json;
using System.Windows;
using Color_it.game;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using TexturePackerLoader;

namespace Color_it
{
    /// <summary>
    /// Ядро программы
    /// </summary>
    /// @noop Используйте GameCore.Core.ResourceManager для получения необходимых ресурсов!
    /// @noop Позже приведу этот класс в нормальный вид
    public class GameCore : Game//TODO дописать класс
    {
        private SpriteSheet _spriteSheet;

        public  GameCore()
        {
            Content.RootDirectory = "Content";
            GraphicsDeviceManager = new GraphicsDeviceManager(this);
            //var sheetCount = Directory.GetFiles("assets").Sum(file => (file.Contains("textures-") && file.Contains(".png")) ? 1 : 0);
        }

        public GraphicsDeviceManager GraphicsDeviceManager { get; }

        public SpriteSheet SpriteSheet
        {
            get => _spriteSheet;
        }

        public ResourceManager ResourceManager { get; } =
            ResourceManager.CreateFileBasedResourceManager("assets", "assets",null);

        public GameSettings Settings { get; } = GameSettings.Load();
        
        public Viewport SubGameViewport { get; } = new(0,0,1000,1000);

        public static GameCore Core { get; } = new();

        public ModelViewController CurrentMvc { get; set; }
        
        /// @brief Выполняет начальную инициализацию игры
        protected override void Initialize()
        {
            base.Initialize();
        }
 
        /// @brief Загружает ресурсы игры
        protected override void LoadContent()
        {
            base.LoadContent();
            _spriteSheet = new SpriteSheetLoader(Content).MultiLoad("textures-{0}", 1);
        }

        protected override void Draw(GameTime gameTime)
        {
            base.Draw(gameTime);
            GraphicsDevice.Clear(Color.Purple);
        }

        protected override void Update(GameTime gameTime)
        {
            base.Update(gameTime);
        }

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
                return File.Exists(SettingsFilePath) ? JsonSerializer.Deserialize<GameSettings>(File.ReadAllText(SettingsFilePath, Encoding.UTF8)) : new GameSettings();
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
