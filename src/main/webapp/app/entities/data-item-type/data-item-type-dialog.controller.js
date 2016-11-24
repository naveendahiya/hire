(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('DataItemTypeDialogController', DataItemTypeDialogController);

    DataItemTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DataItemType'];

    function DataItemTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DataItemType) {
        var vm = this;

        vm.dataItemType = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.dataItemType.id !== null) {
                DataItemType.update(vm.dataItemType, onSaveSuccess, onSaveError);
            } else {
                DataItemType.save(vm.dataItemType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:dataItemTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
