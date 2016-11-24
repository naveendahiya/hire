(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('SavedListDeleteController',SavedListDeleteController);

    SavedListDeleteController.$inject = ['$uibModalInstance', 'entity', 'SavedList'];

    function SavedListDeleteController($uibModalInstance, entity, SavedList) {
        var vm = this;

        vm.savedList = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SavedList.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
