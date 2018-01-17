/**
 * Created by neo on 26/09/2017.
 * <p>
 * 描述：
 * 用 bfs 或者 dfs 搜索出一张 10X10 地图里的一条从起点到终点可行路径，如果没有可行路径，打印无路径信息即可。
 * 提供的 Tools 类可以辅助生成一张 10X10 随机地图以及打印地图信息
 * 地图中的每一个格子信息都是用 Node 类描述的
 * <p>
 * 要求：
 * <p>
 * 1. 给出一个随机地图（10X10），每一个地图格子都有其对应的状态（参见下面 STATUS 值）和起点、终点
 * 在所给的地图里寻找一条可以从起点走向终点的路径，并用 STATUS.road 标记该格子状态
 * 2. 把每一步 "行走" 过程（地图格子信息被更改的过程）都打印出来（调用方法 prtMap(nodes:Node[][]) 即可）
 * 3*. 重写／修改方法 prtMap，把行走的步数打印在地图对应的格子里面
 */

/**
 * 地图某一个格子的状态
 */
enum STATUS {
    empty,  // 空白信息，你可以根据需要创建更多的状态来标识一个格子
    open,   // 可以行走
    closed, // 已经走过
    wall,   // 墙
    road,   // 最终确定生成的路
    start,  // 起点
    end     // 终点
}


class Tools {
    public static Node[][] getRandomMap() {
        Node[][] nodes = new Node[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                double randNum = Math.random();
                Node node = new Node(i, j, randNum > 0.2d ? STATUS.open : STATUS.wall);
                nodes[i][j] = node;
            }
        }
        nodes[1][1].status = STATUS.start;  // 起点
        nodes[8][8].status = STATUS.end;    // 终点
        return nodes;
    }


    /**
     * 传入一张 node 二维数组（称之为地图矩阵），可以打印该地图
     *
     * @param nodes
     */
    public static void prtMap(Node[][] nodes) {
        for (int i = 0; i < 100; i++) System.out.print("\n");
        for (Node[] nodex : nodes) {
            for (Node nodey : nodex) {
                char p = '□';
                if (STATUS.closed == nodey.status) p = '•';
                else if (STATUS.wall == nodey.status) p = '■';
                else if (STATUS.road == nodey.status) p = '☆';
                else if (STATUS.start == nodey.status) p = '☃';
                else if (STATUS.end == nodey.status) p = '☺';
                System.out.print(" " + p + " ");
            }
            System.out.print("\n");
        }
        try {
            Thread.sleep(1000); // 休息 1 秒（隔 1 秒打印一次）
        } catch (Exception e) {
            // 进程休眠异常，不作处理
        }
    }
}

/**
 * 格子
 */
class Node {
    STATUS status;  // 格子状态（种类，可以是：墙、已走过的路、未走的路、起点格子、终点格子、标记的最终路线格子等）
    int x;          // 横轴
    int y;          // 纵轴

    Node(int x, int y, STATUS status) {
        this.x = x;
        this.y = y;
        this.status = status;
    }
}

/**
 * 在此类里面作答
 * <p>
 * 给出的示例代码是：生成一张随机地图，打印该地图
 */
public class SearchTest {
    public static void main(String[] args) {
        // 获取地图信息
        Node[][] nodes = Tools.getRandomMap();
        // 打印地图
        Tools.prtMap(nodes);

        // 或者这样调用来打印很多张不同的随机地图（虽然并没有什么x用。。）
        Tools.prtMap(Tools.getRandomMap());
        Tools.prtMap(Tools.getRandomMap());
        Tools.prtMap(Tools.getRandomMap());
        Tools.prtMap(Tools.getRandomMap());
        Tools.prtMap(Tools.getRandomMap());
        Tools.prtMap(Tools.getRandomMap());
    }
}