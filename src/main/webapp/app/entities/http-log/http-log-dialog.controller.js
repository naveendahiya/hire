(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('HttpLogDialogController', HttpLogDialogController);

    HttpLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HttpLog'];

    function HttpLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, HttpLog) {
        var vm = this;

        vm.httpLog = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.httpLog.id !== null) {
                HttpLog.update(vm.httpLog, onSaveSuccess, onSaveError);
            } else {
                HttpLog.save(vm.httpLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:httpLogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
