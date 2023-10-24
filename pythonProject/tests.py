from main import connectionTest, inputNumPostTest


def testConnetion1():
    res = connectionTest("", "")
    assert res != "Авторизация успешна"


def testConnetion2():
    res = connectionTest("+79379658586", "1212")
    assert res != "Авторизация успешна"


def testConnetion3():
    res = connectionTest("+79379658586", "drwr34431212")
    assert res != "Авторизация успешна"


def testConnetion4():
    res = connectionTest("", "drwr34431212")
    assert res != "Авторизация успешна"


def testConnetion5():
    res = connectionTest("+79379658586", "D1318565d")
    assert res == "Авторизация успешна"




def testCount1():
    count = inputNumPostTest(10)
    assert count == 10

def testCount2():
    count = inputNumPostTest("fdssd")
    assert count == 100

def testCount3():
    count = inputNumPostTest("")
    assert count == 100

def testCount4():
    count = inputNumPostTest(100000)
    assert count == 100

def testCount5():
    count = inputNumPostTest(-10)
    assert count == 100

