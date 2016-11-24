(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('SavedSearchDeleteController',SavedSearchDeleteController);

    SavedSearchDeleteController.$inject = ['$uibModalInstance', 'entity', 'SavedSearch'];

    function SavedSearchDeleteController($uibModalInstance, entity, SavedSearch) {
        var vm = this;

        vm.savedSearch = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SavedSearch.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
