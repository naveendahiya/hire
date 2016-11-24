(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('HttpLogTypesDialogController', HttpLogTypesDialogController);

    HttpLogTypesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HttpLogTypes'];

    function HttpLogTypesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, HttpLogTypes) {
        var vm = this;

        vm.httpLogTypes = entity;
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
            if (vm.httpLogTypes.id !== null) {
                HttpLogTypes.update(vm.httpLogTypes, onSaveSuccess, onSaveError);
            } else {
                HttpLogTypes.save(vm.httpLogTypes, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:httpLogTypesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
