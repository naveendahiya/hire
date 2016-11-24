(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('EeoEthnicTypeDialogController', EeoEthnicTypeDialogController);

    EeoEthnicTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EeoEthnicType'];

    function EeoEthnicTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EeoEthnicType) {
        var vm = this;

        vm.eeoEthnicType = entity;
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
            if (vm.eeoEthnicType.id !== null) {
                EeoEthnicType.update(vm.eeoEthnicType, onSaveSuccess, onSaveError);
            } else {
                EeoEthnicType.save(vm.eeoEthnicType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:eeoEthnicTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
