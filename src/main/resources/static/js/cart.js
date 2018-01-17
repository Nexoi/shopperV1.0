/**
 * Created by neo on 03/09/2017.
 * cart option
 */
$(document).ready(function () {
    // 初始化 header 的购物车信息作用
    flushCartNum();
});

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toGMTString();
    var path = "path=/";
    document.cookie = cname + "=" + cvalue + "; " + expires + "; " + path;
}
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
    }
    return "";
}
// var products = [
//     {
//         pid: 33,
//         qty: 2,
//         norm:"9_73"
//     },
//     {
//         pid: 2,
//         qty: 1,
//         norm:"67_69"
//     }
// ]

function addIntoCart(pid, norm) {
    // var products = getCookie("products");
    // if (products == "") {
    //     // 新建购物记录
    //     var pro = [];
    //     pro.push({pid: pid, qty: 1, norm: norm});
    //     toProductsString(pro);
    //     alert("Added successfully!");
    //     flushCartNum();
    //     return;
    // }
    var pro = parseProducts();
    for (var i = 0; i < pro.length; i++) {
        // 遍历 cart
        if (pro[i].pid == pid && pro[i].norm == norm) {
            pro[i].qty += 1;
            toProductsString(pro);
            alert("Added successfully!");
            flushCartNum();
            return;
        }
    }
    // 新建购物记录
    pro.push({pid: pid, qty: 1, norm: norm});
    toProductsString(pro);
    alert("Added successfully!");
    flushCartNum();
    return;
}
/**
 * 刷新 header 的购物车提示
 */
function flushCartNum() {
    // 遍历 cookie products
    var pro = parseProducts();
    var count = 0;
    for (var i = 0; i < pro.length; i++) {
        // 遍历 cart
        count += pro[i].qty;
    }
    $("#cartNum").html(count);
}

/**
 * setCartNum(23,-1,1) 表示减一操作
 * @param pid
 * @param num
 * @param type
 */
// 修改 cart 产品数量 [在购物车页面操作]
function setCartNum(pid, norm, num, type) {
    // type 有两种：0. 重置; 1. 追加
    num = parseInt(num);
    var pro = parseProducts();
    for (var i = 0; i < pro.length; i++) {
        // 遍历 cart
        if (pro[i].pid == pid && pro[i].norm == norm) {
            if (type == 0) {
                pro[i].qty = num;
            } else {
                pro[i].qty = parseInt(pro[i].qty) + num;
            }
            toProductsString(pro);
            flushCartNum();
            return;
        }
    }
    // 执行添加
    addNewRecord(pid, norm, num);
}
function deleteCartItem(pid, norm) {
    var pro = parseProducts();
    for (var i = 0; i < pro.length; i++) {
        if (pro[i].pid == pid && pro[i].norm == norm) {
            pro.splice(i,1);
        }
    }
    toProductsString(pro);
}
function addNewRecord(pid, norm, qty) {
    pro = parseProducts();
    pro.push({pid: pid, qty: qty, norm: norm});
    toProductsString(pro);
    flushCartNum();
}

function parseProducts() {
    try {
        var str = getCookie("products");
        if (str == "") {
            return [];
        }
        return JSON.parse(str.replace(/\(/g, "{").replace(/\)/g, "}").replace(/v/g, ":").replace(/w/g, "\"").replace(/x/g, ","));
    } catch (e) {
        // 清空购物车数据
        setCookie("products", "", 30);// 存一个月
        return [];
    }
}
function toProductsString(json) {
    var str = JSON.stringify(json).replace(/{/g, "(").replace(/}/g, ")").replace(/:/g, "v").replace(/"/g, "w").replace(/,/g, "x");
    setCookie("products", str, 30);// 存一个月
}


///////coupon///////
function setCoupon(coupon){
    setCookie("coupon",coupon,30);
}
