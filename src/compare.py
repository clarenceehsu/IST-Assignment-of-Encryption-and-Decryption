import os

path = os.path.dirname(os.path.realpath(sys.executable))

def compare():
    try:
        with open(path + '\\test.txt', 'r', encoding='utf-8') as f:
            article1 = f.readlines()
            print('The test.txt is loaded.')

        with open(path + '\\test_recover.txt', 'r', encoding='utf-8') as f:
            article2 = f.readlines()
            print('The test_raw.txt is loaded.')
    except IOError:
        print("Sorry, the files are not existed.\n")
        a = input("Press any key to continue...")

    if article1 == article2:
        print('\nCongratulations! These two files are same!\n')

if __name__ == "__main__":
    compare()
    a = input("Press any key to continue...")
    print(a)