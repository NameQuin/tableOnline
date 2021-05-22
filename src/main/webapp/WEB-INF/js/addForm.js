let app = new Vue({
    el: "#app",
    data: {
        // tempIndex: 0,
        //tempVarList: {}, // 放在单选组里面进行数据绑定，不绑定会导致无法点击，无论是否在一个组中都需要绑定数据，不然无法点击
        checkList: [], // 放在checkbox-group里面绑定，保证复选框能正常显示，复选框可以不放在组内就不用绑定数据，也能被点击
        radio: "text",
        formTitle: "", // 表单名称
        name: "", // 字段名
        isReq: false, // 是否为必填字段
        submitToDb: false, // 是否保存数据至数据库
        rgroup: { // 可选的选项
            r1: '',
            r2: '',
            r3: '',
            r4: ''
        },
        formConfig: [], // 暂存的新增组件的数据
        info: "", // 提示信息
        fieldInDB: [], // 数据库中已有字段
        selectField: "", // 数据库中字段被选中的值
        canEdit: true,
        isNew: true,
        textDisable: false,
        gradeList: [{id: 1, value: "2016"}, {id: 2, value: "2017"}, {id: 3, value: "2018"}], // 年级列表
        grades: [], // 选择年级信息
        departmentList: [], // 院系列表
        departments: [], // 选中的院系
        majorList: [], // 专业列表
        majors: [], // 选中的专业
        classList: [], // 班级列表
        classes: [], // 选中的班级
        studentList: [], // 学生列表
        students: [], // 选中的学生列表
        startTime: "", // 开始时间
        endTime: "", // 结束时间
        options1: {}, // 开始时间的限制条件
        options2: {} // 结束时间的限制条件
    },
    mounted: function() {
        // 将已有的字段信息拉取
        this.getFieldsInfo();
        // 获取年级信息
        this.getAllGrades();
        // 起始时间的限制条件
        this.options1 = this.startTimeLimit();
        this.options2 = this.endTimeLimit();
    },
    methods: {
        startTimeLimit(){ // 开始时间限制函数
            let that = this;
            return {
                disabledDate(time) {
                    let date = that.endTime;
                    if(date !== ""){
                        return time.getTime() < new Date() - 8.64e7 || time.getTime() > date;
                    }else{
                        return time.getTime() < new Date() - 8.64e7;
                    }
                }
            }
        },
        endTimeLimit(){ // 结束时间限制函数
            let that = this;
            return {
                disabledDate(time){
                    let date = that.startTime;
                    if(date !== ""){
                        return time.getTime() < date;
                    }else{
                        return time.getTime() < new Date() - 8.64e7;
                    }
                }
            }
        },
        getAllGrades: function(){ // 获取所有的年级信息
            let _this = this;
            $.ajax({
                type: "GET",
                url: "/tableOnline/admin/getGrade",
                dataType: "json",
                success: function (res) {
                    if(res.code === 200){
                        _this.gradeList = res.data;
                        // console.log(JSON.stringify(res.data));
                    }
                },
                error: function (err) {
                    console.log("服务器出错", err);
                }
            });
        },
        getFieldsInfo: function() { // 获取已有字段信息
            let _this = this;
            $.ajax({
                type: "GET",
                url: "/tableOnline/admin/getAllKeys",
                dataType: "json",
                success: function (res) {
                    if(res.code === 200){
                        _this.fieldInDB = res.data; // 数据给到app的数据
                        // console.log(JSON.stringify(res.data));
                    }
                },
                error: function (err) {
                    console.log("服务器出错", err);
                }
            });
        },
        getId: function() { // 获得当前已存在组件的数量作为id
            return this.formConfig.length;
        },
        addComponent: function(type) { // 将新增的组件信息存数数组中
            if(!this.name) {
                this.info = "请输入字段名";
                return;
            }
            if(type === 'select' || type === 'radio' || type === 'checkbox') {
                if(this.isNew){ // 新增字段的组件对象生成
                    let targetOption = [];
                    let count = 0;
                    for(let k in this.rgroup){
                        if(this.rgroup[k]){
                            targetOption.push(this.rgroup[k]);
                            count++;
                        }
                    }
                    // 保证选项值大于等于2才能提交
                    if(count < 2){
                        this.info = "选项值不得少于两个";
                        return;
                    }
                    this.$set(this.formConfig,this.getId(),{
                        id: this.getId(),
                        type: type,
                        label: this.name,
                        options: targetOption,
                        req: this.isReq,
                        canEdit: this.canEdit,
                        newField: this.isNew,
                        hasOption: true
                    });
                }else{ // 数据库已有字段的组件对象生成
                    let fieldInfo = this.fieldInDB[this.selectField];
                    let options = [];
                    if(fieldInfo.ktypevalue !== undefined && fieldInfo.ktypevalue !== null && fieldInfo.ktypevalue !== ""){
                        options = fieldInfo["ktypevalue"].split("&");
                    }
                    let kid = fieldInfo["kid"];
                    let name = fieldInfo["kcnname"];
                    let targetOption = [];
                    for(let i = 0; i < options.length; i++){
                        targetOption.push(options[i]);
                    }
                    this.$set(this.formConfig, this.getId(), {
                        id: this.getId(),
                        kid: kid,
                        type: type,
                        label: name,
                        options: targetOption,
                        req: this.isReq,
                        canEdit: this.canEdit,
                        newField: this.isNew,
                        hasOption: true
                    });
                }
            }else {
                if(this.isNew){
                    this.$set(this.formConfig,this.getId(),{
                        id: this.getId(),
                        type: type,
                        label: this.name,
                        req: this.isReq,
                        canEdit: this.canEdit,
                        newField: this.isNew,
                        hasOption: false
                    });
                }else{
                    let fieldInfo = this.fieldInDB[this.selectField];
                    let kid = fieldInfo["kid"];
                    let name = fieldInfo["kcnname"];
                    this.$set(this.formConfig, this.getId(), {
                        id: this.getId(),
                        kid: kid,
                        type: type,
                        label: name,
                        req: this.isReq,
                        canEdit: this.canEdit,
                        newField: this.isNew,
                        hasOption: false
                    })
                }
            }
            // 将选项对象恢复原状
            this.clearAddKV();
            this.info = "";
            this.name = '';
            this.isReq = false;
            this.isNew = true;
            this.canEdit = true;
            this.textDisable = false;
        },
        handleEditForm: function() {
            if(this.formConfig.length < 1){
                this.$message({
                    message: '还未添加表单项',
                    center: true
                });
                return;
            }
            if(this.formTitle === ""){
                this.$message({
                   message: "表单未添加标题",
                   center: true
                });
                return;
            }
            if(this.startTime === "" || this.endTime === ""){
                this.$alert('时间范围还未选择', '提示', {
                    confirmButtonText: '确定',
                });
                return;
            }
            this.$confirm('即将提交，是否选择了发布条件?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() =>{
                // 格式化时间
                let timeObj = new Array();
                timeObj.push(this.dateFormat(this.startTime));
                timeObj.push(this.dateFormat(this.endTime));
                // 构造提交数据
                let destObj = {
                    formTitle: this.formTitle,
                    submitToDb: this.submitToDb,
                    grades: this.grades,
                    departments: this.departments,
                    majors: this.majors,
                    classes: this.classes,
                    students: this.students,
                    timeRange: timeObj,
                    formData: this.formConfig
                };
                // console.log(JSON.stringify(destObj));
                let that = this;
                // 提交请求
                $.ajax({
                    type: "POST",
                    contentType : "application/json;charset=UTF-8", // 提交json数据给后端
                    url: "/tableOnline/admin/createForm",
                    data: JSON.stringify(destObj),
                    dataType: "json",
                    success: function (res) {
                        if(res.code === 200){
                            console.log("成功信息：", res.msg);
                            that.$message({
                                message: "提交成功",
                                center: true
                            });
                            setTimeout(() => location.href = "/tableOnline/pages/admin/formList.html", 1500);
                        }else{
                            console.log("失败信息：", res);
                            that.$message({
                                message: "提交失败，请重试",
                                center: true
                            });
                        }
                    },
                    error: function (err) {
                        console.log("请求失败：", err);
                        that.$message({
                            message: "服务器错误",
                            center: true
                        });
                    }
                });
            }).catch(() =>{
                console.log("提交取消");
            });
        },
        addOption: function(){ // 增加当前字段的选项
            for(let k in this.rgroup){
                if(!this.rgroup[k]){
                    this.info = "请填写完已有的选项框再增加";
                    return;
                }
            }
            this.info = "";
            let len = Object.keys(this.rgroup).length;
            this.$set(this.rgroup, "r"+(++len), "");
        },
        clearAddKV: function(){// 清除掉新增的键值对，保证可选项默认都是4个
            let n = 1;
            for(let k in this.rgroup){
                if(n > 4){
                    delete this.rgroup[k];
                }else{
                    this.rgroup[k] = "";
                }
                n++;
            }
            this.$forceUpdate();// 强制重新渲染，将改边同步到页面上
        },
        radioChange: function(){ // 单选框改变选中状态，group的change事件不会给处理函数传递值
            this.clearAddKV(); //清除掉新增的选项键值对，恢复默认值，防止新增了选项但是并未提交直接切换了字段类型导致数据未被修正回默认值
            this.isReq = false; // 将是否必选也恢复默认值
            this.info = ""; // 清空提示信息
            this.isNew = true;
            this.canEdit = true;
            this.textDisable = false;
            this.name = "";
        },
        delComponent: function(index, row, id){ // 删除表项
            // console.log("index: ", index); //表格中的行号，从0开始
            // console.log("row: ", row); // 改行表格的对象
            // delete this.formConfig[id]; // 只会将该位置的内容变成undefined，Vue.delete会将该位置数据删除

            this.$delete(this.formConfig, index); // 删除指定位置的组件
            this.formConfig.forEach(function(v, i){ // 修改剩余元素的序号
                v.id = i;
            });
        },
        upComponent: function(index, row, id){ // 将数据行上移
            let from = this.formConfig[index];
            let to = this.formConfig[index-1];
            from.id = to.id;
            to.id = id;
            this.$set(this.formConfig, index, to);
            this.$set(this.formConfig, index-1, from);
        },
        downComponent: function(index, row, id){ // 将数据行下移
            let from = this.formConfig[index];
            let to = this.formConfig[index+1];
            from.id = to.id;
            to.id = id;
            this.$set(this.formConfig, index, to);
            this.$set(this.formConfig, index+1, from);
        },
        dbFieldChange: function(i){ // 选中已有字段的下拉列表
            // console.log(i);
            let option = this.fieldInDB[i];
            this.name = option.kcnname; // 修改字段名为当前选中字段名
            this.radio = option.ktype; // 类型更改为当前选中类型
            this.isNew = false; // 不是新增字段
            this.canEdit = false; // 默认不可编辑
            this.textDisable = true; // 设置字段名不可编辑
            this.info = ""; // 清空提示信息
        },
        selectFinish1: function(status){ // 下拉列表选择框消失，完成触发事件
            // console.log("选中改变");
            // 根据年级获取院系信息
            if(!status){
                let _this = this;
                let gradeData = [];
                if(_this.grades === null || _this.grades.length === 0){// 不选表示全选
                    for(let i = 0; i < _this.gradeList.length; i++){
                        gradeData.push(_this.gradeList[i].gid);
                    }
                }else{
                    gradeData = _this.grades;
                }
                console.log("年级信息：", gradeData);
                // 清除所有选中信息
                _this.departments = [];
                _this.majors = [];
                _this.classes = [];
                _this.students = [];
                $.ajax({
                    type: "POST",
                    url: "/tableOnline/admin/getDepartment",
                    traditional: true, // 传递数组，不做深度序列化
                    data: {
                        gradeId: gradeData
                    },
                    dataType: "json",
                    success: function (res) {
                        if(res.code === 200){
                            // console.log(JSON.stringify(res.data));
                            _this.departmentList = res.data;
                            _this.$forceUpdate()
                        }
                    },
                    error: function (err) {
                        console.log("上传参数为：", this.data);
                        console.log("服务器错误", err);
                    }
                });
            }
        },
        selectFinish2: function(status){ // 下拉列表选择完成触发事件
            // 根据院系查找专业信息
            if(!status){
                let _this = this;
                let departmentData = [];
                if(_this.departments === null || _this.departments.length === 0){// 不选表示全选
                    for(let i = 0; i < _this.departmentList.length; i++){
                        departmentData.push(_this.departmentList[i].did);
                    }
                }else{
                    departmentData = _this.departments;
                }
                console.log("院系信息，", departmentData);
                // 清除所有院系以下的选择信息
                _this.majors = [];
                _this.classes = [];
                _this.students = [];
                $.ajax({
                    type: "POST",
                    url: "/tableOnline/admin/getMajor",
                    traditional: true, // 传递数组，不做深度序列化
                    data: {
                        departmentId: departmentData
                    },
                    dataType: "json",
                    success: function (res) {
                        if(res.code === 200){
                            // console.log(JSON.stringify(res.data));
                            _this.majorList = res.data;
                            _this.$forceUpdate()
                        }
                    },
                    error: function (err) {
                        console.log("上传参数为：", this.data);
                        console.log("服务器错误", err);
                    }
                });
            }
        },
        selectFinish3: function(status){ // 下拉列表选择完成触发事件
            // 根据专业查找班级信息
            if(!status){
                let _this = this;
                let majorData = [];
                if(_this.majors === null || _this.majors.length === 0){// 不选表示全选
                    for(let i = 0; i < _this.majorList.length; i++){
                        majorData.push(_this.majorList[i].mid);
                    }
                }else{
                    majorData = _this.majors;
                }
                console.log("专业信息：", majorData);
                // 清除已选择的下级信息
                _this.classes = [];
                _this.students = [];
                $.ajax({
                    type: "POST",
                    url: "/tableOnline/admin/getClazz",
                    traditional: true, // 传递数组，不做深度序列化
                    data: {
                        majorId: majorData
                    },
                    dataType: "json",
                    success: function (res) {
                        if(res.code === 200){
                            // console.log(JSON.stringify(res.data));
                            _this.classList = res.data;
                            _this.$forceUpdate()
                        }
                    },
                    error: function (err) {
                        console.log("上传参数为：", this.data);
                        console.log("服务器错误", err);
                    }
                });
            }
        },
        selectFinish4: function(status){ // 下拉列表选择完成触发事件
            // 根据班级查找学生信息
            if(!status){
                let _this = this;
                let classData = [];
                if(_this.classes === null || _this.classes.length === 0){// 不选表示全选
                    for(let i = 0; i < _this.classList.length; i++){
                        classData.push(_this.classList[i].cid);
                    }
                }else{
                    classData = _this.classes;
                }
                console.log("提交班级信息：", classData);
                // 清除已选择的下级信息
                _this.students = [];
                $.ajax({
                    type: "POST",
                    url: "/tableOnline/admin/getStudent",
                    traditional: true, // 传递数组，不做深度序列化
                    data: {
                        clazzId: classData
                    },
                    dataType: "json",
                    success: function (res) {
                        if(res.code === 200){
                            // console.log(JSON.stringify(res.data));
                            _this.studentList = res.data;
                            _this.$forceUpdate()
                        }
                    },
                    error: function (err) {
                        console.log("上传参数为：", this.data);
                        console.log("服务器错误", err);
                    }
                });
            }
        },
        dateFormat: function(date){ // 对时间进行格式化处理
            let year = date.getFullYear();
            let month = date.getMonth()+1;
            let day = date.getDate();
            let hour = date.getHours();
            let minute = date.getMinutes();
            let second = date.getSeconds();
            const formatNumber = function (n){
                n = n.toString();
                return n[1] ? n : "0"+n;
            };
            let ret = [year, month, day].map(formatNumber).join('-').toString()+" "+[hour, minute, second].map(formatNumber).join(':').toString();
            return ret;
        }

    },
});
