(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('EeoVeteranTypeDialogController', EeoVeteranTypeDialogController);

    EeoVeteranTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EeoVeteranType'];

    function EeoVeteranTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EeoVeteranType) {
        var vm = this;

        vm.eeoVeteranType = entity;
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
            if (vm.eeoVeteranType.id !== null) {
                EeoVeteranType.update(vm.eeoVeteranType, onSaveSuccess, onSaveError);
            } else {
                EeoVeteranType.save(vm.eeoVeteranType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:eeoVeteranTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
