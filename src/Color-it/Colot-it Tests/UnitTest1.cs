using Color_it.game.coloring;
using Color_it.game.lines;
using NUnit.Framework;

namespace Colot_it_Tests
{
    public class Tests
    {
        [SetUp]
        public void Setup()
        {
            _plug = new();
            _linesSubGame = new(_plug);
        }

        private GameEventListener _plug;
        private LinesSubGame _linesSubGame;

        /// @warning Изменение констант потребует редактирования ожидаемых значений тестов.
        [TestCase(0,0)]
        [TestCase(8,8)]
        [TestCase(16, 7)]
        [TestCase(80, 8)]
        public void TEST_LinesController_XCellIndexByOrb(int orb, int wantResult)
        {
            Assert.AreEqual(wantResult, LinesSubGame.LinesController.TEST_XCellIndexByOrb((LinesSubGame.LinesController) _linesSubGame.Controller, orb));
        }
        
        /// @warning Изменение констант потребует редактирования ожидаемых значений тестов.
        [TestCase(1,0)]
        [TestCase(2,0)]
        [TestCase(17, 1)]
        [TestCase(80, 8)]
        public void TEST_LinesController_YCellIndexByOrb(int orb, int wantResult)
        {
            Assert.AreEqual(wantResult, LinesSubGame.LinesController.TEST_YCellIndexByOrb((LinesSubGame.LinesController) _linesSubGame.Controller, orb));
        }

    }
}