using System.Resources;

namespace Color_it
{
    /// <summary>
    /// Ядро программы
    /// </summary>
    /// @noop Используйте GameCore.Core.ResourceManager для получения необходимых ресурсов!
    /// @noop Позже приведу этот класс в нормальный вид
    public class GameCore //TODO дописать класс
    {
        private readonly ResourceManager _resourceManager = 
            ResourceManager.CreateFileBasedResourceManager("resources", "resources",null);

        public ResourceManager ResourceManager => _resourceManager; 

        public static GameCore Core { get; }
    }
}