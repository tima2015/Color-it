using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using Color_it;
using Color_it.game;
using Color_it.game.coloring;
using Color_it.game.lines;
using Color_it.game.match_three;
using Color_it.game.snake;

namespace Color_it_Subgame_Test
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {

        private SubGame _current = null;
        private GameCore _core;

        public MainWindow()
        {
            InitializeComponent();
            BeginCore();
        }

        protected override void OnClosed(EventArgs e)
        {
            base.OnClosed(e);
            DisposeCore();
        }

        private void BeginCore()
        {
            _core = new GameCore();
            _core.GraphicsDeviceManager.PreferredBackBufferWidth = 1024;
            _core.GraphicsDeviceManager.PreferredBackBufferHeight = 1024;
            Thread thread = new Thread(() => _core.Run());
            thread.Start();
        }

        private void DisposeCore()
        {
            lock (_core)
            {
                _core.Exit();
            }
        }

        private void BeginSubGame(SubGame subGame)
        {
            lock (_core)
            {
                _core.CurrentMvc = subGame;
            }
        }

        private void Button_Lines_Click(Object sender, RoutedEventArgs e)
        {
            BeginSubGame(new LinesSubGame(new GameEventListener()));
        }
        
        private void Button_Match_Three_Click(Object sender, RoutedEventArgs e)
        {
            BeginSubGame(new MatchThreeSubGame(new GameEventListener()));
        }
        
        private void Button_Snake_Click(Object sender, RoutedEventArgs e)
        {
            BeginSubGame(new SnakeSubGame(new GameEventListener()));
        }
    }
}