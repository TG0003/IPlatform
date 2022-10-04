<!--
 * @Author: _t
 * @Date: 2022-06-28 15:07:05
 * @LastEditTime: 2022-07-16 19:44:11
 * @LastEditors: _t
 * @Description: 
-->
<template>
    <el-menu class="um" router mode="horizontal">
        <el-submenu index="me">
            <template slot="title">
                <span style="margin-right:5px;">{{userInfo.data.username}}</span>
                <el-avatar :size="30" :src="userInfo.data.head+'?t='+Math.random()"></el-avatar>
            </template>
            <el-menu-item @click="exit()">
                退出
            </el-menu-item>
        </el-submenu>
    </el-menu>
</template>
<script>
import $ from 'jquery';
export default {
data() {
    return {
    };
},
computed:{
    userInfo(){
        this.$store.commit('changeUserInfo',JSON.parse(localStorage.getItem('userInfo')||'{}'));
        return this.$store.state.userInfo;
    }
},
watch:{
},
methods:{
    exit(){
        this.$confirm('确定需要退出', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            $('.um').css('display','none');
            localStorage.removeItem('token');
            this.$store.commit('changeUserToken',localStorage.getItem('token'));
            localStorage.removeItem('userInfo');
            this.$store.commit('changeUserInfo',JSON.parse(localStorage.getItem('userInfo')||'{}'));
            this.$router.push('/login');
            this.$message.success('退出成功');
        }).catch(()=>{});
    }
},
mounted(){
    //v-if v-else不能及时响应产生的样式问题
    $('.lg').css('display','none');
    let f = $('.el-submenu__title');
    f.css('border-bottom',0);
}
}
</script>